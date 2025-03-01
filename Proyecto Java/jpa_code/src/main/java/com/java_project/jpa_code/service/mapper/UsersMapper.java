package com.java_project.jpa_code.service.mapper;

import com.java_project.jpa_code.dto.UsersDto;
import com.java_project.jpa_code.dto.UsersDtoPsw;
import com.java_project.jpa_code.model.Users;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * Mapper para la entidad Users y sus DTO UsersDto y UsersDtoPsw.
 * Implementa la interfaz AbstractServiceMapper.
 * Se utiliza la librería ModelMapper para mapear los atributos de la entidad y el DTO.
 */
@Service
public class UsersMapper extends AbstractServiceMapper<Users, UsersDto> {

    private final ModelMapper modelMapper = new ModelMapper();

    // Convertir de entidad a dto
    @Override
    public UsersDto toDto(Users entidad) {
        final UsersDto dto = new UsersDto();
        modelMapper.map(entidad, dto);
        dto.setNombreEmail(entidad.getNombreUsuario() + entidad.getEmail());
        dto.setActive(entidad.isActive()); // Asegúrate de mapear el campo active
        return dto;
    }

    /**
     * Convierte un DTO a una entidad.
     * @param dto DTO a convertir.
     * @return Entidad convertida.
     */
    @Override
    public Users toEntity(UsersDto dto) {
        final Users entidad = new Users();
        modelMapper.map(dto, entidad);
        entidad.setActive(dto.isActive()); // Hay que asegurarse de mapear el campo active
        return entidad;
    }

    /**
     * Convierte un DTO con contraseña a una entidad.
     * @param dto DTO a convertir.
     * @return Entidad convertida.
     */
    public Users toEntityPsw(UsersDtoPsw dto) {
        final Users entidad = new Users();
        modelMapper.map(dto, entidad);
        entidad.setRol(dto.getRoles().iterator().next()); // Hay que asegurarse de mapear el campo rol
        return entidad;
    }

    public UsersMapper() {
    }
}
