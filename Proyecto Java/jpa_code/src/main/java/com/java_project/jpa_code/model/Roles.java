package com.java_project.jpa_code.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * Clase que representa un rol.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false)
    private String rolName;

    @Column(nullable = false)
    private Integer showOnCreate;

    @Column(nullable = false)
    private String rolName_en;

    @Column(nullable = false)
    private String rolName_es;

    @Column(nullable = false)
    private String rolName_01;

    @Column(nullable = false)
    private String rolName_02;

    @Column(nullable = false)
    private String rolName_03;

    /**
     * Usuarios que tienen este rol.
     * Relación uno a muchos con la tabla users.
     * Un rol puede tener varios usuarios.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rol")
    private Set<Users> usuarios;
}
