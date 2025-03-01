package com.java_project.jpa_code.service.mapper;

import com.java_project.jpa_code.dto.DtoDatos_Excel;
import com.java_project.jpa_code.model.Datos_Excel;
import org.mapstruct.Mapper;

/**
 * Mapper para la entidad Datos_Excel y su DTO DtoDatos_Excel.
 * Implementa la interfaz AbstractServiceMapper.
 */
@Mapper(componentModel = "spring")
public abstract class Datos_ExcelMapper extends AbstractServiceMapper<Datos_Excel, DtoDatos_Excel> {

    public abstract DtoDatos_Excel entityToDto(Datos_Excel datosExcel);

    // Implementación generada automáticamente por MapStruct
    public abstract Datos_Excel dtoToEntity(DtoDatos_Excel datosExcelDTO);

    @Override
    public abstract DtoDatos_Excel toDto(Datos_Excel entity);

    @Override
    public abstract Datos_Excel toEntity(DtoDatos_Excel dto) throws Exception;

    // MapStruct no soporta directamente métodos que manejan listas o sets,
    // así que se pueden dejar los métodos de lista y set heredados de AbstractServiceMapper,
    // o proporcionar implementaciones específicas si es necesario.
}