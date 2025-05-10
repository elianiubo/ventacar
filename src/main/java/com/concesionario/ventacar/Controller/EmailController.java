package com.concesionario.ventacar.Controller;

import com.concesionario.ventacar.Service.EmailService;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;


    @PostMapping("/confirmacion")
    public ResponseEntity<String> enviarCorreoConfirmacion(
            @RequestParam String destinatario,
            @RequestParam String vehiculo,
            @RequestParam int precio) {
        try {
            emailService.enviarCorreoConfirmacion(destinatario, vehiculo, precio);
            return ResponseEntity.ok("Correo de confirmación enviado.");
        } catch (MessagingException | IOException | DocumentException e) {
            return ResponseEntity.status(500).body("Error al enviar el correo de confirmación: " + e.getMessage());
        }
    }


    @PostMapping("/reserva")
    public ResponseEntity<String> enviarCorreoReserva(
            @RequestParam String destinatario,
            @RequestParam String vehiculo,
            @RequestParam String fechaReserva,
            @RequestParam int precio) {
        try {
            emailService.enviarCorreoReserva(destinatario, vehiculo, fechaReserva, precio);
            return ResponseEntity.ok("Correo de reserva enviado.");
        } catch (MessagingException | IOException | DocumentException e) {
            return ResponseEntity.status(500).body("Error al enviar el correo de reserva: " + e.getMessage());
        }
    }
}