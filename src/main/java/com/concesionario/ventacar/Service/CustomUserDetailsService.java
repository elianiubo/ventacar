package com.concesionario.ventacar.Service;

import com.concesionario.ventacar.Model.User;
import com.concesionario.ventacar.Repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio personalizado que implementa {@link UserDetailsService} para proporcionar
 * detalles de usuario necesarios para la autenticación de Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor que inyecta el repositorio de usuarios.
     *
     * @param userRepository el repositorio para acceder a los datos de los usuarios.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carga un usuario desde la base de datos usando su correo electrónico y convierte
     * sus roles en autoridades compatibles con Spring Security.
     *
     * @param email el correo electrónico del usuario.
     * @return un objeto {@link UserDetails} con las credenciales y roles del usuario.
     * @throws UsernameNotFoundException si no se encuentra ningún usuario con el correo proporcionado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Convierte los roles del usuario a una colección de autoridades de Spring Security
        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());

        // Retorna el objeto User de Spring Security con email, contraseña y roles
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}