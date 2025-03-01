package com.java_project.jpa_code.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de la seguridad de la aplicación.
 * Define un bean para la configuración de la seguridad.
 * Define un bean para la configuración de los roles.
 * Define un bean para la configuración de la autorización.
 * Define un bean para la configuración de la autenticación.
 */
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(encoder);
        return authenticationProvider;
    }

    /**
     * Configuración de la seguridad de la aplicación.
     * @param http Objeto de configuración de la seguridad.
     * @return Objeto de configuración de la seguridad.
     * @throws Exception Excepción lanzada si se produce un error.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(form -> form
                        .loginPage("/users/login")
                        .failureUrl("/login-error")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/js/**", "/assets/**", "/css/**").permitAll()
                        .requestMatchers("/", "/users/registro", "/users/login",
                                "/users/hasOlvidadoTuPassword",
                                "/proyect", "/contact", "/termsandconditions").permitAll()
                        .requestMatchers(HttpMethod.POST, "/**").permitAll()
                        .requestMatchers( "/users/resetpass/**").permitAll()
                        .requestMatchers("/aplicacion/**").hasRole("USER")
                        .requestMatchers("/gestion/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/accessDenied")
                )

                // Los cors y csrf están deshabilitados para poder hacer peticiones desde el front
                .csrf(csrf -> csrf.disable()) // Esto habrá que cambiar si se lanza a producción
                .cors(cors -> cors.disable()) // Esto habrá que cambiar si se lanza a producción
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

    // Se añade el prefijo ROLE_ a los roles para que Spring Security los reconozca
    @Bean
    public static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("ROLE_");
    }
}