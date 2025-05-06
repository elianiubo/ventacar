package com.concesionario.ventacar.Controller;

import com.concesionario.ventacar.Service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/confirmacion")
    public ResponseEntity<String> enviarCorreoConfirmacion(
            @RequestParam String destinatario,
            @RequestParam String detalles,
            @RequestParam String rutaPdf) {
        try {
            emailService.enviarCorreoConfirmacion(destinatario, detalles, rutaPdf);
            return ResponseEntity.ok("Correo de confirmación enviado.");
        } catch (MessagingException e) {
            return ResponseEntity.status(500). body("Error al enviar el correo: " + e.getMessage());
        }
    }

    @PostMapping("/reserva")
    public ResponseEntity<String> enviarCorreoReserva(
            @RequestParam String destinatario,
            @RequestParam String vehiculo,
            @RequestParam String fecha) {
        try {
            emailService.enviarCorreoReserva(destinatario, vehiculo, fecha);
            return ResponseEntity.ok("Correo de reserva enviado.");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Error al enviar el correo de reserva: " + e.getMessage());
        }
    }
}
