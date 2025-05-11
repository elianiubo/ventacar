package com.concesionario.ventacar.Repository;

import com.concesionario.ventacar.Model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio para acceder y manipular los vehículos en la base de datos.
 * Esta interfaz extiende {@link JpaRepository} para proporcionar las operaciones CRUD básicas.
 * Permite realizar búsquedas de vehículos por marca, tipo, precio y realizar búsquedas filtradas personalizadas.
 */
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    /**
     * Busca una lista de vehículos por su marca, ignorando mayúsculas y minúsculas.
     *
     * @param marca la marca del vehículo a buscar.
     * @return una lista de vehículos que coinciden con la marca proporcionada.
     */
    List<Vehiculo> findByMarcaIgnoreCase(String marca);

    /**
     * Busca una lista de vehículos por su tipo.
     *
     * @param tipo el tipo de vehículo a buscar (por ejemplo, "SUV", "sedán", etc.).
     * @return una lista de vehículos que coinciden con el tipo proporcionado.
     */
    List<Vehiculo> findByTipo(String tipo);

    /**
     * Busca una lista de vehículos cuyo precio esté dentro del rango especificado.
     *
     * @param precioMin el precio mínimo de los vehículos a buscar.
     * @param precioMax el precio máximo de los vehículos a buscar.
     * @return una lista de vehículos cuyo precio esté entre los valores proporcionados.
     */
    List<Vehiculo> findByPrecioBetween(Integer precioMin, Integer precioMax);

    /**
     * Realiza una búsqueda filtrada de vehículos según varios parámetros (marca, tipo, precio mínimo, precio máximo).
     * Los filtros se aplican solo si los valores correspondientes no son nulos o vacíos.
     *
     * @param marca la marca del vehículo a buscar. Puede ser null o vacío para no aplicar el filtro.
     * @param tipo el tipo de vehículo a buscar. Puede ser null o vacío para no aplicar el filtro.
     * @param min el precio mínimo de los vehículos a buscar. Puede ser null para no aplicar el filtro.
     * @param max el precio máximo de los vehículos a buscar. Puede ser null para no aplicar el filtro.
     * @return una lista de vehículos que coinciden con los filtros proporcionados.
     */
    @Query("SELECT v FROM Vehiculo v WHERE " +
            "(:marca IS NULL OR :marca = '' OR LOWER(v.marca) LIKE LOWER(CONCAT('%', :marca, '%'))) AND " +
            "(:tipo IS NULL OR :tipo = '' OR LOWER(v.tipo) LIKE LOWER(CONCAT('%', :tipo, '%'))) AND " +
            "(:min IS NULL OR v.precio >= :min) AND " +
            "(:max IS NULL OR v.precio <= :max)")
    List<Vehiculo> buscarVehiculos(@Param("marca") String marca,
                                   @Param("tipo") String tipo,
                                   @Param("min") Integer min,
                                   @Param("max") Integer max);
}