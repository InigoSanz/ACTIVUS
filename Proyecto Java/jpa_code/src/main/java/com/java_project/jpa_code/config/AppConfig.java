package com.java_project.jpa_code.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * Clase de configuración de la aplicación.
 * Importa la configuración de la capa MVC.
 * Define un bean para encriptar contraseñas.
 * Define un bean para leer propiedades de un archivo de propiedades.
 * Define un bean para realizar peticiones HTTP.
 */
@Configuration
@Import({MvcConfig.class})
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}