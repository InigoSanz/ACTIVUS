package com.java_project.jpa_code.web.controller;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Clase abstracta que contiene métodos comunes para los controladores.
 *
 * @param <OBJ> Objeto que se va a manejar en el controlador.
 */
public abstract class AbstractController<OBJ> {

    protected AbstractController() {
    }

    // Literal para los números de página
    protected final String pageNumbersAttributeKey = "pageNumbers";

    /**
     * Método que devuelve una lista con los números de página.
     *
     * @param obj Objeto que contiene la información de la página.
     * @return Lista con los números de página.
     */
    protected List<Integer> dameNumPaginas(Page<OBJ> obj) {
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = obj.getTotalPages();
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return pageNumbers;
    }
}