package com.concesionario.ventacar.Controller;

import com.concesionario.ventacar.Model.User;
import com.concesionario.ventacar.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Endpoint para sign-up
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestParam String username, @RequestParam String password, @RequestParam(defaultValue = "false") boolean isAdmin) {
        try {
            authService.registerUser(username, password, isAdmin);
            return ResponseEntity.ok("Usuario registrado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User authenticatedUser = authService.authenticateUser(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            return ResponseEntity.ok("Login correcto");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login incorrecto");
    }
}
