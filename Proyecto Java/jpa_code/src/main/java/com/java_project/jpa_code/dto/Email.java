package com.java_project.jpa_code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) de un correo electrónico.
 * Se utiliza para enviar correos electrónicos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    String to;
    String from;
    String subject;
    String content;

    private Map<String, Object> model; // Mapa de objetos que se utilizan para rellenar el contenido del correo electrónico
}
