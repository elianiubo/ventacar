package com.concesionario.ventacar.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.concesionario.ventacar.Service.PdfService; // Ensure this path matches the actual location of PdfService
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.DocumentException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")

public class PdfController {

    @Autowired
    private PdfService pdfService;


    @GetMapping("/generate")
    public String generatePdf() {
        try {

            pdfService.createPdf("src/main/resources/static/pdf/Factura.pdf");
            return "PDF generado con exito";
        }  catch (IOException | DocumentException e) {

        return "Error generating PDF: " + e.getMessage();
        }
    }
}
