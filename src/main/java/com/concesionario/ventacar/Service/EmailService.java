package com.concesionario.ventacar.Service;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servicio para el envío de correos electrónicos con archivos PDF adjuntos,
 * como confirmaciones de compra o reservas de vehículos.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PdfService pdfService;

    /**
     * Envía un correo de confirmación de compra al usuario autenticado,
     * incluyendo un archivo PDF adjunto con los detalles.
     *
     * @param vehiculo nombre o descripción del vehículo comprado.
     * @param precio   precio del vehículo.
     * @throws MessagingException si ocurre un error al enviar el correo.
     * @throws IOException        si ocurre un error al generar el PDF.
     * @throws DocumentException  si ocurre un error en el documento PDF.
     */
    public void enviarCorreoConfirmacion(String vehiculo, int precio)
            throws MessagingException, IOException, DocumentException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String destinatario = auth.getName();

        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File pdfFile = pdfService.createPdf(destinatario, vehiculo, fecha, precio);

        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(destinatario);
        helper.setSubject("Confirmación de la compra");
        helper.setText("Gracias por realizar la compra de su vehículo. Aquí tiene los detalles:\n\n" +
                "Vehículo: " + vehiculo + "\n" +
                "Precio: " + precio + "\n" +
                "Fecha: " + fecha + "\n");

        helper.addAttachment("Factura.pdf", pdfFile);
        javaMailSender.send(mensaje);
    }

    /**
     * Envía un correo de confirmación de reserva con un archivo PDF adjunto.
     *
     * @param destinatario   dirección de correo del cliente.
     * @param vehiculo       nombre o modelo del vehículo reservado.
     * @param fechaReserva   fecha en formato 'yyyy-MM-dd'.
     * @param precio         precio del vehículo reservado.
     * @throws MessagingException si ocurre un error al enviar el correo.
     * @throws IOException        si ocurre un error al generar el PDF.
     * @throws DocumentException  si ocurre un error en el documento PDF.
     */
    public void enviarCorreoReserva(String destinatario, String vehiculo, String fechaReserva, int precio)
            throws MessagingException, IOException, DocumentException {

        Date fecha = parseFecha(fechaReserva);

        File pdfFile = pdfService.createPdf(destinatario, vehiculo, fechaReserva, precio);

        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(destinatario);
        helper.setSubject("Vehículo reservado");
        helper.setText("Ha reservado su vehículo: " + vehiculo + "\n" +
                "Fecha de la reserva: " + fechaReserva + "\n" +
                "Precio: " + precio);

        helper.addAttachment("Reserva.pdf", pdfFile);
        javaMailSender.send(mensaje);
    }

    /**
     * Convierte una fecha en formato texto ('yyyy-MM-dd') a un objeto {@link Date}.
     *
     * @param fechaReserva la fecha como cadena.
     * @return un objeto {@link Date} representando la fecha.
     * @throws IllegalArgumentException si el formato es inválido.
     */
    private Date parseFecha(String fechaReserva) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(fechaReserva);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha de reserva no válida: " + fechaReserva);
        }
    }
}