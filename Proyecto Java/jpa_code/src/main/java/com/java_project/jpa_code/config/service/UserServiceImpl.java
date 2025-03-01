package com.java_project.jpa_code.config.service;

import com.java_project.jpa_code.repository.UsersRepository;
import com.java_project.jpa_code.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementación de la interfaz UserService.
 * Proporciona métodos para codificar contraseñas de usuario.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String getEncodedPassword(Users usuario) {
        String passwd = usuario.getPassword();
        return passwordEncoder.encode(passwd);
    }

    @Override
    public String getEncodedPasswordstr(String pasw_str) {
        return passwordEncoder.encode(pasw_str);
    }
}