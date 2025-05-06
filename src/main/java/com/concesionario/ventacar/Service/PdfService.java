package com.concesionario.ventacar.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

@Service
public class PdfService {

    public File createPdf(String emailCliente, String vehiculo, String fechaReserva, int precio) throws IOException, DocumentException {
        File tempFile = File.createTempFile("factura_", ".pdf");
        // File preparation
        FileOutputStream file = new FileOutputStream(tempFile);
        Document document = new Document();
        PdfWriter.getInstance(document, file);
        document.open();
        // Logo image
        Image image = Image.getInstance("src/main/resources/static/img/Ventacar_logo.png");
        image.scalePercent(40);
        image.setAlignment(Element.ALIGN_CENTER);
        document.add(image);

        // direccio IOC
        String[] direccion = {
                "Av. del Paral·lel 71 – 73",
                "Sants-Montjuïc",
                "08004",
                "Barcelona, España"
        };
        // for loop direccio s'escriu en una linia diferent
        for (String line : direccion) {
            Paragraph p = new Paragraph(line, FontFactory.getFont(FontFactory.HELVETICA, 10));
            p.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);
        }
        document.add(Chunk.NEWLINE);

        Paragraph invoiceTitle = new Paragraph("Factura", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
        // gives space to next values
        invoiceTitle.setSpacingBefore(20);
        invoiceTitle.setSpacingAfter(10);
        document.add(invoiceTitle);

        // Invoice table 2 columns with label and value
        // para parte de la info
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new int[] { 1, 2 });

        addInfoCell(infoTable, "Factura Numero:");
        addInfoCell(infoTable, "F01", true);

        addInfoCell(infoTable, "Fecha:");
        addInfoCell(infoTable, formaterDate());
        addInfoCell(infoTable, "Nombre:");
        /* addInfoCell(infoTable, "Elia Niubo Burgos"); */

        addInfoCell(infoTable, "Correo:");
        /* addInfoCell(infoTable, "elia.nibu@gmail.com"); */

        document.add(infoTable);

        document.add(Chunk.NEWLINE);

        // Table for product details price etc
        PdfPTable productoTable = new PdfPTable(4);
        productoTable.setSpacingBefore(10);
        productoTable.setSpacingAfter(10);
        productoTable.setWidthPercentage(100);
        productoTable.setWidths(new float[] { 8, 4, 4, 6 });

        // Invoice values
        productoTable.addCell(getHeaderCell("Articulo"));
        productoTable.addCell(getHeaderCell("Cantidad"));
        productoTable.addCell(getHeaderCell("Vat"));
        productoTable.addCell(getHeaderCell("Precio"));
        // variables que se reemplazaran por valores de la BD
        String coche = "Mini Turismo";
        int cantidad = 1;
        double vat = 21.0;
        int total = precio * cantidad;
        double precioSinIva = precio / 1.21;
        double vatValor = total - precioSinIva;
        int intVatValor = (int) vatValor;
        int intPrecioSinIva = (int) precioSinIva;

        productoTable.addCell(getNormalCell(vehiculo));
        productoTable.addCell(getNormalCell(String.valueOf(cantidad)));
        productoTable.addCell(getNormalCell("21%"));
        productoTable.addCell(getNormalCell("€ " + total));

        productoTable.setSpacingAfter(80);
        document.add(productoTable);

        // Resumen de totales
        Paragraph exVAT = new Paragraph("Precio (Excl. VAT):    € " + intPrecioSinIva,
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        Paragraph vatLine = new Paragraph("VAT (21%):                  € " + intVatValor,
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        Paragraph totalIncl = new Paragraph("Total (Incl. VAT):        € " + total,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
        // se posiciona a la izquierda
        exVAT.setAlignment(Element.ALIGN_LEFT);
        vatLine.setAlignment(Element.ALIGN_LEFT);
        totalIncl.setAlignment(Element.ALIGN_LEFT);

        document.add(exVAT);
        document.add(vatLine);
        document.add(totalIncl);

        // footer
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

    private void addInfoCell(PdfPTable table, String text) {
        addInfoCell(table, text, false);
    }

    // table info para la factura
    private void addInfoCell(PdfPTable table, String text, boolean bold) {
        Font font = bold
                ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)
                : FontFactory.getFont(FontFactory.HELVETICA, 10);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    // tabla header de la factura, cantidad, precio, vat
    private PdfPCell getHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        return cell;
    }

    // tabla de la factura con los valores respectivos
    private PdfPCell getNormalCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);

        return cell;
    }

    // formatea la fecha de la factura
    private String formaterDate() {
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yy");
        String formattedDate = formatter.format(date);

        return formattedDate;
    }


}
