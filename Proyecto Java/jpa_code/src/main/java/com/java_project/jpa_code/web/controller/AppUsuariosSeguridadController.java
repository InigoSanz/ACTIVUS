package com.java_project.jpa_code.web.controller;

import com.java_project.jpa_code.config.service.UserService;
import com.java_project.jpa_code.dto.RoleDTO;
import com.java_project.jpa_code.dto.UsersDto;
import com.java_project.jpa_code.dto.UsersDtoPsw;
import com.java_project.jpa_code.model.Users;
import com.java_project.jpa_code.service.RolesService;
import com.java_project.jpa_code.service.UsersService;
import com.java_project.jpa_code.util.CadenaAleatoria;
import com.java_project.jpa_code.util.ValidarFormatoPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controlador para gestionar la seguridad de los usuarios.
 */
@Controller
public class AppUsuariosSeguridadController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private final UsersService service;
    private final RolesService roleService;

    public AppUsuariosSeguridadController(UsersService service, RolesService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    /**
     * Método que muestra la vista de registro de usuarios.
     *
     * @param interfazConPantalla Modelo para pasar datos a la vista.
     * @return Vista de registro de usuarios.
     */
    @GetMapping("/users/registro")
    public String vistaRegistro(Model interfazConPantalla) {
        // Instancia en memoria del dto que informa a la vista
        final UsersDtoPsw usuarioDtoPsw = new UsersDtoPsw();
        // Se obtiene la lista de roles
        final List<RoleDTO> roleDTOList = roleService.buscarTodosAlta();
        // Mediante "addAttribute" se comparte con la vista
        interfazConPantalla.addAttribute("datosUsuario", usuarioDtoPsw);
        interfazConPantalla.addAttribute("listaRoles", roleDTOList);
        return "users/register";
    }

    /**
     * Método que guarda un nuevo usuario.
     *
     * @param usuarioDtoPsw Datos del usuario.
     * @param redirectAttributes Atributos para la redirección.
     * @return Redirección a la vista de registro de usuarios.
     * @throws Exception En caso de error.
     */
    @PostMapping("/users/registro")
    public String guardarUsuario(@ModelAttribute(name = "datosUsuario") UsersDtoPsw usuarioDtoPsw, RedirectAttributes redirectAttributes) throws Exception {
        if (ValidarFormatoPassword.ValidarFormato(usuarioDtoPsw.getPassword())) {
            Users usuario = service.getMapper().toEntityPsw(usuarioDtoPsw);
            String encodedPassword = passwordEncoder.encode(usuarioDtoPsw.getPassword());
            usuarioDtoPsw.setPassword(encodedPassword);

            // Por seguridad se modifica el token para que sea obligatorio pedir de nuevo el cambio de password
            String newToken = CadenaAleatoria.obtenerToken(250);
            usuarioDtoPsw.setToken(newToken);
            UsersDto usuario1 = this.service.guardar(usuarioDtoPsw);
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/users/registro";
        } else {
            return "users/register";
        }
    }
}