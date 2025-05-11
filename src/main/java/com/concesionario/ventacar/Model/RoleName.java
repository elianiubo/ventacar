package com.concesionario.ventacar.Model;

/**
 * Enumeración que define los nombres de los roles en el sistema.
 * Los roles son asignados a los usuarios para determinar sus permisos y accesos.
 * <p>
 * Los valores disponibles son:
 * <ul>
 *     <li><b>ROLE_USER</b>: Representa un rol de usuario normal.</li>
 *     <li><b>ROLE_ADMIN</b>: Representa un rol de administrador con privilegios elevados.</li>
 * </ul>
 * </p>
 */
public enum RoleName {
    /**
     * Rol de usuario normal con permisos limitados.
     */
    ROLE_USER,

    /**
     * Rol de administrador con permisos elevados para gestionar el sistema.
     */
    ROLE_ADMIN
}