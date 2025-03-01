package com.java_project.jpa_code.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Clase que se encarga de manejar las propiedades de la aplicación.
 * Se encarga de leer las propiedades del archivo application.properties.
 */
@Configuration
@ConfigurationProperties(prefix = "general")
@Getter
@Setter
public class ConfiguationProperties {

    /**
     * Propiedad que almacena la dirección IP del servidor de Python.
     */
    private String ippythonserver = "192.168.200.128" ;
}