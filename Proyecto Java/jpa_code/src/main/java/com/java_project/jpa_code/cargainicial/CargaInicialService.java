package com.java_project.jpa_code.cargainicial;

import com.java_project.jpa_code.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CargaInicialService {

    private final RolesRepository rolesRepository;

    @Autowired
    public CargaInicialService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }
}