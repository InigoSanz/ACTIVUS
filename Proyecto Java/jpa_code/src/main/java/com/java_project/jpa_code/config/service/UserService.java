package com.java_project.jpa_code.config.service;

import com.java_project.jpa_code.model.Users;
import org.springframework.stereotype.Service;

/**
 * Interfaz que define los métodos que se deben implementar en la clase UserServiceImpl.
 */
@Service
public interface UserService {
    /**
     * Codifica la contraseña de un usuario y devuelve la contraseña codificada.
     *
     * @param usuario el modelo de usuario
     * @return la contraseña codificada como una cadena
     */
    String getEncodedPassword(Users usuario);

    /**
     * Codifica una cadena de contraseña en texto plano y devuelve la contraseña codificada.
     *
     * @param pasw_str la cadena de contraseña en texto plano
     * @return la contraseña codificada como una cadena
     */
    String getEncodedPasswordstr(String pasw_str);
}