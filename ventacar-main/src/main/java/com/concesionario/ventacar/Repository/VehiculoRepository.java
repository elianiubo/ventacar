package com.concesionario.ventacar.Repository;

import com.concesionario.ventacar.Model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findByMarcaIgnoreCase(String marca);

    List<Vehiculo> findByTipo(String tipo);

    List<Vehiculo> findByPrecioBetween(Integer precioMin, Integer precioMax);
}
