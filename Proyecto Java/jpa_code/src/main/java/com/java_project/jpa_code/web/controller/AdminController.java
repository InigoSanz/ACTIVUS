package com.java_project.jpa_code.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para las vistas de administrador.
 */
@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

    @GetMapping("/gestion/usuarios")
    public String gestionUsuarios() {
        return "gestion/gestionUsuarios";
    }

    @GetMapping("/gestion/configuracion")
    public String gestionConfiguracion() {
        return "gestion/gestionConfiguracion";
    }
}