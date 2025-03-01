package com.java_project.jpa_code.service.mapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase abstracta que se encarga de la conversión de entidades a DTOs y viceversa.
 * @param <E> Entidad
 * @param <DTO> DTO
 */
public abstract class AbstractServiceMapper<E, DTO> {

    // Convertir de entidad a dto
    public abstract DTO toDto(E e);

    // Convertir de dto a entidad
    public abstract E toEntity(DTO dto) throws Exception;

    // Conversión de listas de dtos a entidades
    public List<E> toEntity(List<DTO> dtos) throws Exception {
        List<E> list = new ArrayList<>();
        for (DTO dto : dtos) {
            E e = this.toEntity(dto);
            list.add(e);
        }
        return list;
    }

    // Conversión de listas de entidades a dtos
    public List<DTO> toDto(List<E> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    // Conversión de sets de dtos a entidades
    public Set<E> toEntity(Set<DTO> dtos) throws Exception {
        Set<E> eSet = new HashSet<>();
        for (DTO item : dtos) {
            E e = this.toEntity(item);
            eSet.add(e);
        }
        return eSet;
    }

    // Conversión de sets de entidades a dtos
    public Set<DTO> toDto(Set<E> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }
}