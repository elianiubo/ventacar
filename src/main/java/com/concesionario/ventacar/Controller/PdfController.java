package com.concesionario.ventacar.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.concesionario.ventacar.Service.PdfService; // Asegúrate de que esta ruta coincida con la ubicación real de PdfService
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

/**
 * Controlador para gestionar la generación de archivos PDF.
 * Contiene el endpoint necesario para generar un archivo PDF con los detalles de un vehículo.
 */
@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    /**
     * Servicio encargado de la creación de archivos PDF.
     */
    @Autowired
    private PdfService pdfService;

    /**
     * Endpoint para generar un archivo PDF con los detalles de un vehículo.
     * El PDF se genera con la información del usuario, el vehículo, la fecha y el precio.
     *
     * @return un mensaje indicando si el PDF fue generado con éxito o si ocurrió un error.
     */
    @GetMapping("/generate")
    public String generatePdf() {
        try {
            // Genera un PDF con los detalles de un vehículo específico
            pdfService.createPdf("prueba@example.com", "Mini Turismo", "2025-05-06", 15000);
            return "PDF generado con éxito";
        } catch (IOException | DocumentException e) {
            return "Error generando el PDF: " + e.getMessage();
        }
    }
}