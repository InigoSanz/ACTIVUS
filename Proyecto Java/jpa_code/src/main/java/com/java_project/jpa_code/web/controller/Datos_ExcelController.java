package com.java_project.jpa_code.web.controller;

import com.java_project.jpa_code.service.Datos_ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para gestionar la carga de datos desde un archivo Excel.
 */
@RestController
public class Datos_ExcelController {

    @Autowired
    private Datos_ExcelService excelService;

    /**
     * Método que maneja la carga de un archivo Excel.
     *
     * @param file Archivo Excel recibido.
     * @return Respuesta HTTP indicando el resultado de la operación.
     */
    @PostMapping("/api/excel/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se recibió ningún archivo");
        }
        try {
            excelService.cargarDatosDesdeExcel(file.getInputStream());
            return ResponseEntity.ok("Archivo cargado con éxito y datos guardados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo: " + e.getMessage());
        }
    }
}
