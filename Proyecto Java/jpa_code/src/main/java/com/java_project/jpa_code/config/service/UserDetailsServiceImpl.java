package com.java_project.jpa_code.config.service;

import com.java_project.jpa_code.config.details.SuperCustomerUserDetails;
import com.java_project.jpa_code.model.Roles;
import com.java_project.jpa_code.model.Users;
import com.java_project.jpa_code.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Esta clase es una implementación personalizada de UserDetailsService para manejar
 * la autenticación de usuarios en la aplicación. Utiliza el UserRepository para
 * buscar y cargar la información del usuario en la base de datos.
 * <br>
 * Es una implementación de la interfaz UserDetailsService de Spring Security.
 * Su propósito es proporcionar una forma de cargar y devolver los detalles del usuario en un objeto UserDetails,
 * que es necesario para la autenticación y autorización en Spring Security.
 * <br>
 * En resumen, esta clase se utiliza para cargar los detalles de usuario
 * (como el nombre de usuario, la contraseña y los roles)
 * desde el repositorio de usuarios de la aplicación y devolverlos en un objeto
 * UserDetails para ser utilizado por Spring Security en la autenticación y autorización de los usuarios.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository; // Inyección de dependencia del UserRepository

    /**
     * Este método es utilizado por Spring Security para buscar y cargar el usuario en función del
     * email proporcionado.
     *
     * @param email Email del usuario a buscar.
     * @return UserDetails Una instancia de UserDetails con los datos del usuario encontrado.
     * @throws UsernameNotFoundException Excepción lanzada si no se encuentra ningún usuario con el email proporcionado.
     */
    @Override
    public SuperCustomerUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el usuario por su email utilizando el UserRepository
        Optional<Users> user = userRepository.findByNombreUsuarioAndActiveTrue(email);

        // Si el usuario es encontrado, crear una instancia de nuestro Custom UserDetails utilizando los datos del usuario
        SuperCustomerUserDetails superCustomerUserDetails = new SuperCustomerUserDetails();
        if (user.isPresent()) {
            Set<Roles> roles = new HashSet<>();
            roles.add(user.get().getRol());
            superCustomerUserDetails.setUsername(email);
            superCustomerUserDetails.setPassword(user.get().getPassword());
            superCustomerUserDetails.setAuthorities(mapRolesToAuthorities(roles));
            superCustomerUserDetails.setUserID(Math.toIntExact(user.get().getId()));
        } else {
            // Si el usuario no es encontrado, lanzar una excepción UsernameNotFoundException
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        }
        return superCustomerUserDetails;
    }

    /**
     * Esta función auxiliar se utiliza para convertir la lista de roles del usuario en una colección de
     * autoridades que pueden ser utilizadas por Spring Security.
     *
     * @param roles Lista de roles del usuario.
     * @return Collection< ? extends GrantedAuthority> Colección de autoridades.
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Roles> roles) {
        // Utilizar streams de Java para mapear cada rol a una instancia de SimpleGrantedAuthority
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRolName()))
                .collect(Collectors.toList()); // Convertir el stream en una lista
    }
}