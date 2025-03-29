package com.concesionario.ventacar.Repository;

import com.concesionario.ventacar.Model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findByMarcaIgnoreCase(String marca);

    List<Vehiculo> findByTipo(String tipo);

    List<Vehiculo> findByPrecioBetween(Integer precioMin, Integer precioMax);


    // filtro de los vehículos
    @Query("SELECT v FROM Vehiculo v WHERE " +
            "(:marca IS NULL OR LOWER(v.marca) = LOWER(:marca)) AND " +
            "(:tipo IS NULL OR v.tipo = :tipo) AND " +
            "(:min IS NULL OR v.precio >= :min) AND " +
            "(:max IS NULL OR v.precio <= :max)")
    List<Vehiculo> buscarVehiculos(@Param("marca") String marca,
                                   @Param("tipo") String tipo,
                                   @Param("min") Integer min,
                                   @Param("max") Integer max);


}
