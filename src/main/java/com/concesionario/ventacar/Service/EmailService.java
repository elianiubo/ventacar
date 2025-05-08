package com.concesionario.ventacar.Service;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PdfService pdfService;


    public void enviarCorreoConfirmacion(String destinatario, String vehiculo, int precio) throws MessagingException, IOException, DocumentException {
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File pdfFile = pdfService.createPdf(destinatario, vehiculo, fecha, precio);

        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(destinatario);
        helper.setSubject("Confirmación de la compra");
        helper.setText("Gracias por realizar la compra de su vehículo. Aquí tiene los detalles:\n\n" +
                "Vehículo: " + vehiculo + "\n" + "Precio: " + precio + "\n" + "Fecha: " + fecha + "\n");


        helper.addAttachment("Factura.pdf", pdfFile);

        javaMailSender.send(mensaje);
    }


    public void enviarCorreoReserva(String destinatario, String vehiculo, String fechaReserva, int precio) throws MessagingException, IOException, DocumentException {
        Date fecha = parseFecha(fechaReserva);

        File pdfFile = pdfService.createPdf(destinatario, vehiculo, fechaReserva, precio);

        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(destinatario);
        helper.setSubject("Vehículo reservado");
        helper.setText("Ha reservado su vehículo: " + vehiculo + "\nFecha de la reserva: " + fechaReserva + "\nPrecio: " + precio);

        helper.addAttachment("Reserva.pdf", pdfFile);

        javaMailSender.send(mensaje);
    }


    private Date parseFecha(String fechaReserva) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(fechaReserva);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha de reserva no válida: " + fechaReserva);
        }
    }
}

