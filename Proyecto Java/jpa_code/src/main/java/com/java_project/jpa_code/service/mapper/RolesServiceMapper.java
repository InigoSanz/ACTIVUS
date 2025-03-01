package com.java_project.jpa_code.service.mapper;

import com.java_project.jpa_code.dto.RoleDTO;
import com.java_project.jpa_code.model.Roles;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * Mapper para la entidad Roles y su DTO RoleDTO.
 * Implementa la interfaz AbstractServiceMapper.
 * Se utiliza la librería ModelMapper para mapear los atributos de la entidad y el DTO.
 */
@Service
public class RolesServiceMapper extends AbstractServiceMapper<Roles, RoleDTO> {

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Convierte un DTO a una entidad.
     * @param dto DTO a convertir.
     * @return Entidad convertida.
     */
    @Override
    public Roles toEntity(RoleDTO dto) {
        Roles entidad = new Roles();
        modelMapper.map(dto, entidad);
        return entidad;
    }

    @Override
    public RoleDTO toDto(Roles entidad) {
        RoleDTO dto = new RoleDTO();
        modelMapper.map(entidad, dto);
        return dto;
    }
}