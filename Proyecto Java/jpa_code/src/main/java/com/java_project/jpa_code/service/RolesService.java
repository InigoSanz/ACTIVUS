package com.java_project.jpa_code.service;

import com.java_project.jpa_code.dto.RoleDTO;
import com.java_project.jpa_code.model.Roles;
import com.java_project.jpa_code.repository.RolesRepository;
import com.java_project.jpa_code.repository.UsersRepository;
import com.java_project.jpa_code.service.mapper.RolesServiceMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la entidad Roles.
 * Implementa la interfaz AbstractBusinessService.
 * Se inyecta el repositorio RolesRepository y el mapper RolesServiceMapper.
 */
@Service
public class RolesService extends AbstractBusinessService<Roles, Integer, RoleDTO, RolesRepository, RolesServiceMapper> {

    private final UsersRepository usuarioRepository;

    /**
     * Constructor de la clase.
     * @param repository Repositorio RolesRepository.
     * @param serviceMapper Mapper RolesServiceMapper.
     * @param usuarioRepository Repositorio UsersRepository.
     */
    protected RolesService(RolesRepository repository, RolesServiceMapper serviceMapper, UsersRepository usuarioRepository) {
        super(repository, serviceMapper);
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Buscar todos los roles que se muestran en la creación de un usuario.
     * @return Lista de DTOs.
     */
    public List<RoleDTO> buscarTodosAlta() {
        return this.getMapper().toDto(this.getRepo().findAllByShowOnCreate(1));
    }
}