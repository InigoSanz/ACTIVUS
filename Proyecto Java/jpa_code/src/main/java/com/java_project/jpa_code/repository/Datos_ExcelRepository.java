package com.java_project.jpa_code.repository;

import com.java_project.jpa_code.model.Datos_Excel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz que representa un repositorio de datos de Excel.
 * Esta interfaz hereda de JpaRepository, lo que permite el uso de métodos CRUD.
 */
public interface Datos_ExcelRepository extends JpaRepository<Datos_Excel, Long> {
    // Añadir métodos personalizados
}