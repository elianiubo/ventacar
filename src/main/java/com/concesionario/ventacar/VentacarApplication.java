package com.concesionario.ventacar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Ventacar.
 *
 * <p>Este es el punto de entrada para iniciar la aplicación Spring Boot.
 * Utiliza {@code @SpringBootApplication} para habilitar la configuración automática,
 * el escaneo de componentes y otras funcionalidades clave del framework Spring.</p>
 */
@SpringBootApplication
public class VentacarApplication {

	/**
	 * Método principal que lanza la aplicación.
	 *
	 * @param args argumentos de línea de comandos (opcional).
	 */
	public static void main(String[] args) {
		SpringApplication.run(VentacarApplication.class, args);
	}
}
