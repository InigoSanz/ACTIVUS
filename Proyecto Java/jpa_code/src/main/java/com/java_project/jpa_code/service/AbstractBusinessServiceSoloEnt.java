package com.java_project.jpa_code.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

/**
 * Clase abstracta que define los métodos básicos de un servicio de negocio.
 * @param <E> Tipo de la entidad.
 * @param <ID> Tipo del id de la entidad.
 * @param <REPO> Tipo del repositorio.
 */
public abstract class AbstractBusinessServiceSoloEnt<E, ID, REPO extends JpaRepository<E, ID>> {
    private final REPO repo;

    protected AbstractBusinessServiceSoloEnt(REPO repo) {
        this.repo = repo;
    }

    public List<E> buscarEntidades() {
        return this.repo.findAll();
    }

    public Set<E> buscarEntidadesSet() {
        return new HashSet<>(this.repo.findAll());
    }

    public Optional<E> encuentraPorIdEntity(ID id) {
        return this.repo.findById(id);
    }

    public Optional<E> encuentraPorId(ID id) {
        return this.repo.findById(id);
    }

    public Page<E> buscarTodos(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Set<E> buscarTodosSet() {
        return new HashSet<>(this.repo.findAll());
    }

    // Guardar
    public E guardar(E entidad) {
        return repo.save(entidad);
    }

    public void guardar(List<E> ents) {
        for (E e : ents) {
            repo.save(e);
        }
    }

    // Eliminar un registro
    public void eliminarPorId(ID id) {
        this.repo.deleteById(id);
    }

    // Obtener el repo
    public REPO getRepo() {
        return repo;
    }
}