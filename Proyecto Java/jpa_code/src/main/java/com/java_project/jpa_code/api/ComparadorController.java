package com.java_project.jpa_code.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Clase que se encarga de manejar las peticiones HTTP relacionadas con el comparador de medicamentos.
 */
@RestController
@RequestMapping("/api")
public class ComparadorController {

    private final RestTemplate restTemplate;

    public ComparadorController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Método que se encarga de enviar una petición POST a la API en Python para comparar dos medicamentos.
     * @param medicamentos Mapa con los nombres de los medicamentos a comparar.
     * @return ResponseEntity con la respuesta de la API en Python.
     */
    @PostMapping("/pa_medicamentos")
    public ResponseEntity<?> compararMedicamentos(@RequestBody Map<String, String> medicamentos) {
        String pythonApiUrl = "http://127.0.0.1:5000/pa_medicamentos"; // URL de la API en Python

        ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, medicamentos, Map.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}