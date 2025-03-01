package com.java_project.jpa_code.repository;

import com.java_project.jpa_code.model.Menu;
import com.java_project.jpa_code.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Interfaz que representa un repositorio de menús.
 * Esta interfaz hereda de JpaRepository, lo que permite el uso de métodos CRUD.
 */
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findDistinctByRolesIn(Collection<Roles> roles);

    List<Menu> findDistinctByRolesInAndActiveTrue(Collection<Roles> roles);

    List<Menu> findDistinctByRolesInAndActiveTrueOrderByOrder(Collection<Roles> roles);
}