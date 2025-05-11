package com.concesionario.ventacar.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servicio para la generación de archivos PDF con los detalles de compra o reserva de un vehículo.
 * Utiliza la biblioteca iText para crear documentos PDF de manera programática.
 */
@Service
public class PdfService {

    /**
     * Crea un archivo PDF temporal con los detalles de la compra o reserva.
     *
     * @param emailCliente   correo electrónico del cliente.
     * @param vehiculo       nombre o descripción del vehículo.
     * @param fechaReserva   fecha de la reserva o compra (formato: yyyy-MM-dd).
     * @param precio         precio del vehículo.
     * @return un objeto {@link File} que representa el PDF generado.
     * @throws IOException         si ocurre un error al escribir el archivo.
     * @throws DocumentException   si ocurre un error al generar el documento PDF.
     */
    public File createPdf(String emailCliente, String vehiculo, String fechaReserva, int precio) throws IOException, DocumentException {
        File tempFile = File.createTempFile("factura_", ".pdf");
        FileOutputStream file = new FileOutputStream(tempFile);
        Document document = new Document();
        PdfWriter.getInstance(document, file);
        document.open();

        // Agrega el logo
        Image image = Image.getInstance("src/main/resources/static/img/Ventacar_logo.png");
        image.scalePercent(40);
        image.setAlignment(Element.ALIGN_CENTER);
        document.add(image);

        // Dirección del concesionario
        String[] direccion = {
                "Av. del Paral·lel 71 – 73",
                "Sants-Montjuïc",
                "08004",
                "Barcelona, España"
        };
        for (String line : direccion) {
            Paragraph p = new Paragraph(line, FontFactory.getFont(FontFactory.HELVETICA, 10));
            p.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);
        }
        document.add(Chunk.NEWLINE);

        // Título de la factura
        Paragraph invoiceTitle = new Paragraph("Factura", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
        invoiceTitle.setSpacingBefore(20);
        invoiceTitle.setSpacingAfter(10);
        document.add(invoiceTitle);

        // Tabla con información básica
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new int[]{1, 2});

        addInfoCell(infoTable, "Factura Numero:");
        addInfoCell(infoTable, "F01", true);
        addInfoCell(infoTable, "Fecha:");
        addInfoCell(infoTable, formaterDate());
        addInfoCell(infoTable, "Correo:");
        addInfoCell(infoTable, emailCliente);

        document.add(infoTable);
        document.add(Chunk.NEWLINE);

        // Tabla de productos
        PdfPTable productoTable = new PdfPTable(4);
        productoTable.setSpacingBefore(10);
        productoTable.setSpacingAfter(10);
        productoTable.setWidthPercentage(100);
        productoTable.setWidths(new float[]{8, 4, 4, 6});

        productoTable.addCell(getHeaderCell("Artículo"));
        productoTable.addCell(getHeaderCell("Cantidad"));
        productoTable.addCell(getHeaderCell("VAT"));
        productoTable.addCell(getHeaderCell("Precio"));

        int cantidad = 1;
        double vat = 21.0;
        int total = precio * cantidad;
        double precioSinIva = precio / 1.21;
        double vatValor = total - precioSinIva;

        productoTable.addCell(getNormalCell(vehiculo));
        productoTable.addCell(getNormalCell(String.valueOf(cantidad)));
        productoTable.addCell(getNormalCell("21%"));
        productoTable.addCell(getNormalCell("€ " + total));

        productoTable.setSpacingAfter(80);
        document.add(productoTable);

        // Resumen de totales
        Paragraph exVAT = new Paragraph("Precio (Excl. VAT):    € " + (int) precioSinIva,
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        Paragraph vatLine = new Paragraph("VAT (21%):                  € " + (int) vatValor,
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        Paragraph totalIncl = new Paragraph("Total (Incl. VAT):        € " + total,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));

        exVAT.setAlignment(Element.ALIGN_LEFT);
        vatLine.setAlignment(Element.ALIGN_LEFT);
        totalIncl.setAlignment(Element.ALIGN_LEFT);

        document.add(exVAT);
        document.add(vatLine);
        document.add(totalIncl);

        // Footer de agradecimiento
        Paragraph footerText = new Paragraph("Muchas gracias por comprar con nosotros",
                FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.LIGHT_GRAY));
        footerText.setSpacingBefore(100);
        footerText.setAlignment(Element.ALIGN_CENTER);
        document.add(footerText);

        document.close();
        file.close();

        System.out.println("PDF se ha creado");

        return tempFile;
    }

    /**
     * Agrega una celda con texto a la tabla de información (sin negrita).
     *
     * @param table tabla a la que se agregará la celda.
     * @param text  texto de la celda.
     */
    private void addInfoCell(PdfPTable table, String text) {
        addInfoCell(table, text, false);
    }

    /**
     * Agrega una celda con texto a la tabla de información, con opción a negrita.
     *
     * @param table tabla a la que se agregará la celda.
     * @param text  texto de la celda.
     * @param bold  indica si el texto debe ir en negrita.
     */
    private void addInfoCell(PdfPTable table, String text, boolean bold) {
        Font font = bold
                ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)
                : FontFactory.getFont(FontFactory.HELVETICA, 10);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    /**
     * Genera una celda de encabezado para una tabla.
     *
     * @param text texto del encabezado.
     * @return celda formateada.
     */
    private PdfPCell getHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        return cell;
    }

    /**
     * Genera una celda de contenido normal para una tabla.
     *
     * @param text contenido de la celda.
     * @return celda formateada.
     */
    private PdfPCell getNormalCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        return cell;
    }

    /**
     * Formatea la fecha actual en el formato 'dd MM yy'.
     *
     * @return fecha actual como cadena formateada.
     */
    private String formaterDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yy");
        return formatter.format(date);
    }
}