package com.java_project.jpa_code.repository;

import com.java_project.jpa_code.model.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaz que representa un repositorio de roles.
 * Esta interfaz hereda de JpaRepository, lo que permite el uso de métodos CRUD.
 */
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    // Añadir findAll con paginación para obtener los roles
    Page<Roles> findAll(Pageable pageable);

    List<Roles> findAllByShowOnCreate(Integer val); // Método para obtener los roles que se muestran en la creación de usuarios

    Roles findByRolName(String rolName); // Método para obtener un rol por su nombre
}