package com.java_project.jpa_code.service;

import com.java_project.jpa_code.service.mapper.AbstractServiceMapper;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

/**
 * Clase abstracta que define los métodos básicos de un servicio de negocio.
 * @param <E> Tipo de la entidad.
 * @param <ID> Tipo del id de la entidad.
 * @param <DTO> Tipo del DTO.
 * @param <REPO> Tipo del repositorio.
 * @param <MAPPER> Tipo del mapper.
 */
public abstract class AbstractBusinessService<E, ID, DTO, REPO extends JpaRepository<E, ID>, MAPPER extends AbstractServiceMapper<E, DTO>> {

    // Obtenemos el repositorio
    @Getter
    private final REPO repo;
    private final MAPPER serviceMapper;

    protected AbstractBusinessService(REPO repo, MAPPER mapper) {
        this.repo = repo;
        this.serviceMapper = mapper;
    }

    public Optional<E> buscar(ID id) {
        return this.repo.findById(id);
    }

    /**
     * Buscar todos los registros.
     * @return Lista de DTOs.
     */
    public List<DTO> buscarTodos() {
        return this.serviceMapper.toDto(this.repo.findAll());
    }

    public List<E> buscarEntidades() {
        return this.repo.findAll();
    }

    public Set<E> buscarEntidadesSet() {
        return new HashSet<>(this.repo.findAll());
    }

    public Set<DTO> buscarTodosSet() {
        return new HashSet<>(this.serviceMapper.toDto(this.repo.findAll()));
    }

    /**
     * Buscar todos los registros paginados.
     * @param pageable Paginación.
     * @return Página de DTOs.
     */
    public Page<DTO> buscarTodos(Pageable pageable) {
        return repo.findAll(pageable).map(this.serviceMapper::toDto);
    }

    public Page<DTO> buscarTodosAux(Pageable pageable) {
        Page<E> pageEntity = repo.findAll(pageable);
        return pageEntity.map(this.serviceMapper::toDto);
    }

    public Page<E> buscarTodosAuxEnt(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<DTO> encuentraPorId(ID id) {
        return this.repo.findById(id).map(this.serviceMapper::toDto); // Se mapea el resultado a DTO
    }

    public Optional<E> encuentraPorIdEntity(ID id) {
        return this.repo.findById(id);
    }

    /**
     * Guardar un registro.
     * @param dto DTO a guardar.
     * @return DTO guardado.
     * @throws Exception Si ocurre un error al guardar.
     */
    public DTO guardar(DTO dto) throws Exception {
        E entidad = serviceMapper.toEntity(dto);
        E entidadGuardada = repo.save(entidad);
        return serviceMapper.toDto(entidadGuardada);
    }

    public DTO guardarEntidadDto(E entidad) {
        E entidadGuardada = repo.save(entidad);
        return serviceMapper.toDto(entidadGuardada);
    }

    public E guardarEntidadEntidad(E entidad) { // Guardar entidad y devolver la entidad guardada
        return repo.save(entidad);
    }

    public void guardar(List<DTO> dtos) throws Exception {
        for (DTO dto : dtos) {
            E e = serviceMapper.toEntity(dto);
            repo.save(e);
        }
    }

    // Eliminar un registro
    public void eliminarPorId(ID id) {
        this.repo.deleteById(id);
    }

    // Obtener el mapper
    public MAPPER getMapper() {
        return serviceMapper;
    }
}