package com.java_project.jpa_code.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) de un rol.
 */
@Getter
@Setter
public class RoleDTO implements Serializable {
    private Integer id;
    private String rolName;
    private Integer showOnCreate;
    private String rolName_en;
    private String rolName_es;
    private String rolName_01;
    private String rolName_02;
    private String rolName_03;
}