package com.java_project.jpa_code.model;

// Imports
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @Column(name = "descripcionEs", nullable = false)
    private String description_es;

    @Column(nullable = false)
    private String description_en;

    @Column(nullable = false)
    private String description_01;

    @Column(nullable = false)
    private String description_02;

    @Column(nullable = false)
    private String description_03;

    /**
     * Menú padre.
     * Relación muchos a uno con la tabla menu.
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Menu parent;

    /**
     * Orden en el que se mostrará el menú.
     */
    @Column(name = "APP_ORDER")
    private Integer order;

    @Column(nullable = false)
    private Integer active;

    @Column(nullable = false)
    private String url;

    /**
     * Roles que tienen acceso a este menú.
     * Relación muchos a muchos con la tabla roles.
     * Se carga de forma inmediata (EAGER).
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_roles",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Roles> roles; // Roles del menú
}