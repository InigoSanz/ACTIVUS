package com.java_project.jpa_code.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * Clase de configuración de los recursos de la aplicación.
 * Se encarga de definir los recursos de la aplicación.
 */
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/",
            "classpath:/custom/",
            "classpath:/"
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS)
                .setCachePeriod(3000)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:///D:/solovmwarewalgreen/projecto/SEN4CFARMING/api/files/") // Esto habrá que cambiarlo
                .setCachePeriod(3000)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}