package com.java_project.jpa_code.util;

import com.java_project.jpa_code.config.details.SuperCustomerUserDetails;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Clase que contiene métodos para el manejo de eventos de login.
 * Se encarga de mostrar en consola el usuario que se ha logeado.
 */
@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        String username = "vacio";
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        // Comprobamos si hay usuario logeado
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            System.out.println(this.getClass() + ", Usuario no logeado" + " " + "debug");
        } else {
            username = ((SuperCustomerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            System.out.println(this.getClass() + " El usuario es: " + username + " " + "debug");
        }
    }
}