package com.java_project.jpa_code.dto;

import com.java_project.jpa_code.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) de un usuario.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private long id;
    private String email;
    private String nombreUsuario;
    private String nombreEmail;
    private String password;
    private Set<Roles> roles = new HashSet<>(); // Roles del usuario
    private boolean active;
}