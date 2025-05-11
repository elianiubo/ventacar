package com.concesionario.ventacar.Repository;

import com.concesionario.ventacar.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de repositorio para acceder y manipular los usuarios en la base de datos.
 * Esta interfaz extiende {@link JpaRepository} para proporcionar las operaciones CRUD básicas.
 * Permite realizar búsquedas de usuarios por su correo electrónico.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario en la base de datos utilizando su dirección de correo electrónico.
     *
     * @param email la dirección de correo electrónico del usuario a buscar.
     * @return el usuario con la dirección de correo electrónico proporcionada, o null si no se encuentra.
     */
    User findByEmail(String email);
}