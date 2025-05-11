package com.concesionario.ventacar.Model;

import jakarta.persistence.*;

/**
 * Clase que representa un vehículo en el sistema.
 * La clase está mapeada a la tabla `vehiculos` en la base de datos.
 * Cada vehículo tiene una marca, un tipo, un precio, una imagen y una descripción.
 */
@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    /**
     * Identificador único del vehículo en la base de datos.
     * Es generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Marca del vehículo.
     */
    private String marca;

    /**
     * Tipo del vehículo (por ejemplo, SUV, sedán, etc.).
     */
    private String tipo;

    /**
     * Precio del vehículo.
     */
    private Integer precio;

    /**
     * URL o ruta de la imagen del vehículo.
     */
    private String imagen;

    /**
     * Descripción detallada del vehículo.
     */
    private String descripcion;

    /**
     * Constructor vacío de la clase `Vehiculo`.
     * Utilizado para crear una instancia de vehículo sin inicializar los campos.
     */
    public Vehiculo() {
    }

    /**
     * Constructor de la clase `Vehiculo` con parámetros.
     * Este constructor se utiliza para crear un vehículo con todos los atributos inicializados.
     *
     * @param marca marca del vehículo.
     * @param tipo tipo del vehículo.
     * @param precio precio del vehículo.
     * @param imagen URL o ruta de la imagen del vehículo.
     * @param descripcion descripción detallada del vehículo.
     */
    public Vehiculo(String marca, String tipo, Integer precio, String imagen, String descripcion) {
        this.marca = marca;
        this.tipo = tipo;
        this.precio = precio;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el identificador único del vehículo.
     *
     * @return el identificador único del vehículo.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del vehículo.
     *
     * @param id el identificador que se asignará al vehículo.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la marca del vehículo.
     *
     * @return la marca del vehículo.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del vehículo.
     *
     * @param marca la marca que se asignará al vehículo.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el tipo del vehículo.
     *
     * @return el tipo del vehículo.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del vehículo.
     *
     * @param tipo el tipo que se asignará al vehículo.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el precio del vehículo.
     *
     * @return el precio del vehículo.
     */
    public Integer getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del vehículo.
     *
     * @param precio el precio que se asignará al vehículo.
     */
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la URL o ruta de la imagen del vehículo.
     *
     * @return la URL o ruta de la imagen del vehículo.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la URL o ruta de la imagen del vehículo.
     *
     * @param imagen la URL o ruta de la imagen que se asignará al vehículo.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene la descripción del vehículo.
     *
     * @return la descripción del vehículo.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del vehículo.
     *
     * @param descripcion la descripción que se asignará al vehículo.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
