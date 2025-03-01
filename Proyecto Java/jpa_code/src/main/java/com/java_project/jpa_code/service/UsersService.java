package com.java_project.jpa_code.service;

import com.java_project.jpa_code.dto.UsersDto;
import com.java_project.jpa_code.dto.UsersDtoPsw;
import com.java_project.jpa_code.model.Users;
import com.java_project.jpa_code.repository.UsersRepository;
import com.java_project.jpa_code.service.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para la entidad Users.
 * Implementa la interfaz AbstractBusinessService.
 * Se inyecta el repositorio UsersRepository y el mapper UsersMapper.
 */
@Service
public class UsersService extends AbstractBusinessService<Users, Integer, UsersDto, UsersRepository, UsersMapper> {

    @Autowired
    public UsersService(UsersRepository repo, @Qualifier("usersMapper") UsersMapper serviceMapper) {
        super(repo, serviceMapper);
    }

    /**
     * Guardar un usuario con contraseña.
     * @param usuarioDto DTO.
     * @param password Contraseña.
     * @return DTO guardado.
     */
    public UsersDto guardar(UsersDto usuarioDto, String password) {
        System.out.println("usuarioDto:" + usuarioDto.getNombreUsuario());
        final Users entidad = getMapper().toEntity(usuarioDto);
        System.out.println("Entidad:" + entidad.getNombreUsuario());
        entidad.setPassword(password);
        System.out.println("Entidad password:" + entidad.getPassword());
        Users entidadGuardada = getRepo().save(entidad);
        return getMapper().toDto(entidadGuardada);
    }

    /**
     * Guardar un usuario con contraseña.
     * @param usuarioDtoPsw DTO con contraseña.
     * @return DTO guardado.
     */
    public UsersDto guardar(UsersDtoPsw usuarioDtoPsw) {
        System.out.println("usuarioDto:" + usuarioDtoPsw.getNombreUsuario());
        final Users entidad = getMapper().toEntityPsw(usuarioDtoPsw);
        System.out.println("Entidad:" + entidad.getNombreUsuario());
        Users entidadGuardada = getRepo().save(entidad);
        return getMapper().toDto(entidadGuardada);
    }

    /**
     * Guardar una lista de usuarios.
     * @param lUsuarioDto Lista de DTOs.
     */
    @Override
    public void guardar(List<UsersDto> lUsuarioDto) {
        for (UsersDto dto : lUsuarioDto) {
            Users usuario = getMapper().toEntity(dto);
            usuario.setPassword(getRepo().getReferenceById((int) usuario.getId()).getPassword()); // Se obtiene la contraseña del usuario existente
            getRepo().save(usuario);
        }
    }

    public Page<UsersDto> buscarTodos(Pageable pageable, String loggedInUsername) {
        Page<Users> usersPage = getRepo().findAll(pageable);
        List<UsersDto> usersDtoList = usersPage.getContent().stream()
                .filter(user -> !user.getEmail().equals(loggedInUsername))
                .map(user -> getMapper().toDto(user))
                .collect(Collectors.toList());
        return new PageImpl<>(usersDtoList, pageable, usersPage.getTotalElements());
    }

    // Buscar todos los usuarios que se muestran en la creación de un usuario
    public Optional<Users> encuentraPorIdEntity(Integer id) {
        return getRepo().findById(id);
    }
}