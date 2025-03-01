package com.java_project.jpa_code.repository;

import com.java_project.jpa_code.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Interfaz que representa un repositorio de usuarios.
 * Esta interfaz hereda de JpaRepository, lo que permite el uso de métodos CRUD.
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT COUNT(id) FROM Users WHERE nombreUsuario = ?1 AND password = ?2") // Query para validar el usuario y contraseña
    Integer repValidarPassword(String email, String password);

    Users findUserByNombreUsuarioIs(String name);

    Optional<Users> findByNombreUsuarioAndActiveTrue(String name); // Método para obtener un usuario por su nombre de usuario

    Optional<Users> findByNombreUsuarioAndTokenAndActiveTrue(String name, String token); // Método para obtener un usuario por su nombre de usuario y token
    Optional<Users> findUserByEmailAndActiveTrue(String email);

    Page<Users> findAll(Pageable pageable); // Método para obtener todos los usuarios con paginación

    Users findUserByEmailAndPassword(String email, String password);
}