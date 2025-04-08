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
    public void registerUser(String username, String password, boolean isAdmin) {
        if (userRepository.findByUsername(username) != null) {
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

        User user = new User(username, passwordEncoder.encode(password), roles);
        userRepository.save(user);
    }

    //verificar si nombre ya existe
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    //autenticar usuario existente
    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; //correcto
        }
        return null; //incorrecto
    }
}
