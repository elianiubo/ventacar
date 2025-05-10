package com.concesionario.ventacar.Service;

import com.concesionario.ventacar.Model.Role;
import com.concesionario.ventacar.Model.RoleName;
import com.concesionario.ventacar.Model.User;
import com.concesionario.ventacar.Repository.RoleRepository;
import com.concesionario.ventacar.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //registrar nuevo user
    public void registerUser(String email, String password, String nombre, String apellidos, String telefono, String codigoPostal, String fechaNacimiento, boolean isAdmin) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("El usuario ya existe");
        }

        Set<Role> roles = new HashSet<>();

        Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_USER);
        userRole.ifPresent(roles::add);

        if (isAdmin) {
            Optional<Role> adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN);
            adminRole.ifPresent(roles::add);
        }

        if (roles.isEmpty()) {
            throw new RuntimeException("No se pudo asignar ningun rol al usuario");
        }

        User user = new User(email, passwordEncoder.encode(password), nombre, apellidos, telefono, codigoPostal, fechaNacimiento, roles);
        userRepository.save(user);
    }

    //verificar si email ya existe
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    //autenticar usuario existente
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; //correcto
        }
        return null; //incorrecto
    }
}
