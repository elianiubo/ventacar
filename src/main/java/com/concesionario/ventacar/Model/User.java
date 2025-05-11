package com.concesionario.ventacar.Model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa a un usuario en el sistema.
 * La clase está mapeada a la tabla `users` en la base de datos.
 * Un usuario puede tener múltiples roles, que están definidos en la tabla `user_roles`.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Identificador único del usuario en la base de datos.
     * Es generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Dirección de correo electrónico del usuario.
     * Este campo es único y no puede ser nulo.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Contraseña del usuario.
     * Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Nombre del usuario.
     */
    @Column
    private String nombre;

    /**
     * Apellidos del usuario.
     */
    @Column
    private String apellidos;

    /**
     * Número de teléfono del usuario.
     */
    @Column
    private String telefono;

    /**
     * Código postal del usuario.
     */
    @Column
    private String codigoPostal;

    /**
     * Fecha de nacimiento del usuario.
     */
    @Column
    private String fechaNacimiento;

    /**
     * Roles asignados al usuario.
     * Un usuario puede tener múltiples roles, que se gestionan a través de la relación muchos a muchos.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /**
     * Constructor vacío de la clase `User`.
     * Utilizado para crear una instancia de usuario sin inicializar los campos.
     */
    public User() {}

    /**
     * Constructor de la clase `User` con parámetros.
     * Este constructor se utiliza para crear un usuario con todos los atributos inicializados.
     *
     * @param email dirección de correo electrónico del usuario.
     * @param password contraseña del usuario.
     * @param nombre nombre del usuario.
     * @param apellidos apellidos del usuario.
     * @param telefono número de teléfono del usuario.
     * @param codigoPostal código postal del usuario.
     * @param fechaNacimiento fecha de nacimiento del usuario.
     * @param roles conjunto de roles asignados al usuario.
     */
    public User(String email, String password, String nombre, String apellidos, String telefono, String codigoPostal, String fechaNacimiento, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.codigoPostal = codigoPostal;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return el identificador único del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del usuario.
     *
     * @param id el identificador que se asignará al usuario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la dirección de correo electrónico del usuario.
     *
     * @return la dirección de correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece la dirección de correo electrónico del usuario.
     *
     * @param email la dirección de correo electrónico que se asignará al usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password la contraseña que se asignará al usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return el nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre el nombre que se asignará al usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     *
     * @return los apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     *
     * @param apellidos los apellidos que se asignarán al usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el número de teléfono del usuario.
     *
     * @return el número de teléfono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del usuario.
     *
     * @param telefono el número de teléfono que se asignará al usuario.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el código postal del usuario.
     *
     * @return el código postal del usuario.
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Establece el código postal del usuario.
     *
     * @param codigoPostal el código postal que se asignará al usuario.
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     *
     * @return la fecha de nacimiento del usuario.
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del usuario.
     *
     * @param fechaNacimiento la fecha de nacimiento que se asignará al usuario.
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene los roles asignados al usuario.
     *
     * @return el conjunto de roles del usuario.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Establece los roles asignados al usuario.
     *
     * @param roles el conjunto de roles que se asignará al usuario.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
