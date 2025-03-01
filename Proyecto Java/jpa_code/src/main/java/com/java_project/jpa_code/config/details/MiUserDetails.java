package com.java_project.jpa_code.config.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Clase que se encarga de manejar los detalles de un usuario.
 * Implementa la interfaz UserDetails de Spring Security.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiUserDetails implements UserDetails {

    // Campos que son propios de UserDetails
    private String username;
    private String password;
    private String coper_username;
    private String coper_password;
    private boolean isActive = true;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isCopernicusLogged = false;
    private Collection<? extends GrantedAuthority> grantedAuthorities;
    private boolean activo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}