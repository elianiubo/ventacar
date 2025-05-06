package com.concesionario.ventacar.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import org.springframework.core.io.FileSystemResource;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoConfirmacion(String destinatario, String detalles, String rutaPdf) throws MessagingException {
       MimeMessage mensaje = mailSender.createMimeMessage();
       MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

       helper.setTo(destinatario);
       helper.setSubject("Confirmación de la compra");
       helper.setText("Gracias realizar la compra de su vehículo. Aquí tiene los detalles:\n\n" + detalles);

       // Para agregar el pdf
       FileSystemResource file = new FileSystemResource(new File(rutaPdf));
       helper.addAttachment("Factura.pdf", file);

       mailSender.send(mensaje);
    }

    public void enviarCorreoReserva(String destinatario, String vehículo, String fecha) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, false);

        helper.setTo(destinatario);
        helper.setSubject("Vehículo reservado");
        helper.setText("Ha reservado su vehículo: " + vehículo + "\nFecha de la reserva: " + fecha);

        mailSender.send(mensaje);


    }
}
