package com.concesionario.ventacar.Model;

import jakarta.persistence.*;

/**
 * Clase que representa un rol de usuario en el sistema.
 * Utiliza la enumeración `RoleName` para definir el nombre del rol,
 * y es persistida en la base de datos en la tabla `roles`.
 */
@Entity
@Table(name = "roles")
public class Role {

    /**
     * Identificador único del rol en la base de datos.
     * Es generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * El nombre del rol, que es un valor de la enumeración `RoleName`.
     * Este campo es único en la base de datos.
     */
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RoleName name;

    /**
     * Constructor vacío de la clase `Role`.
     * Se utiliza para la creación de instancias de `Role` sin parámetros.
     */
    public Role() {}

    /**
     * Constructor de la clase `Role` con un nombre de rol específico.
     *
     * @param name el nombre del rol que se asignará a este objeto `Role`.
     */
    public Role(RoleName name) {
        this.name = name;
    }

    /**
     * Obtiene el identificador del rol.
     *
     * @return el identificador único del rol.
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el nombre del rol.
     *
     * @return el nombre del rol.
     */
    public RoleName getName() {
        return name;
    }

    /**
     * Establece el nombre del rol.
     *
     * @param name el nombre que se asignará al rol.
     */
    public void setName(RoleName name) {
        this.name = name;
    }
}