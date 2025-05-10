package com.concesionario.ventacar.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.concesionario.ventacar.Service.PdfService; // Ensure this path matches the actual location of PdfService
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")

public class PdfController {

    @Autowired
    private PdfService pdfService;


    @GetMapping("/generate")
    public String generatePdf() {
        try {
            pdfService.createPdf("prueba@example.com", "Mini Turismo", "2025-05-06", 15000);
            return "PDF generado con éxito";
        } catch (IOException | DocumentException e) {
            return "Error generando el PDF: " + e.getMessage();
        }
    }
}
