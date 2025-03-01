package com.java_project.jpa_code.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Clase que representa un usuario.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class        Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nombreUsuario", nullable = false, unique = true)
    private String nombreUsuario;

    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Roles del usuario.
     * Relación muchos a uno con la tabla de roles.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Roles rol;


    @Column(name = "active", nullable = false)
    private boolean active = true; // Por defecto el usuario está activo

    @Column(name = "token", nullable = false, unique = true)
    private String token = UUID.randomUUID().toString(); // Token de autenticación
}