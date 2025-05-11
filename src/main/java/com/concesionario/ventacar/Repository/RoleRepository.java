package com.concesionario.ventacar.Repository;

import com.concesionario.ventacar.Model.Role;
import com.concesionario.ventacar.Model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfaz de repositorio para acceder y manipular los roles en la base de datos.
 * Esta interfaz extiende {@link JpaRepository} para proporcionar las operaciones CRUD básicas.
 * Permite encontrar un rol por su nombre.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Busca un rol en la base de datos por su nombre.
     *
     * @param name el nombre del rol que se busca (por ejemplo, {@link RoleName#ROLE_USER} o {@link RoleName#ROLE_ADMIN}).
     * @return un {@link Optional} que contiene el rol si existe, o vacío si no se encuentra.
     */
    Optional<Role> findByName(RoleName name);
}