package com.java_project.jpa_code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) de un inicio de sesión.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    private String username;
    private String password;
}