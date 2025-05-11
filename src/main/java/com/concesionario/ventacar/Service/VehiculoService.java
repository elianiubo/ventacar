package com.concesionario.ventacar.Service;

import com.concesionario.ventacar.Model.Vehiculo;
import com.concesionario.ventacar.Repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Servicio que gestiona operaciones relacionadas con los vehículos del concesionario.
 * Interactúa con el repositorio para realizar consultas específicas.
 */
@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    /**
     * Constructor para inyectar el repositorio de vehículos.
     *
     * @param vehiculosRepository el repositorio de vehículos.
     */
    public VehiculoService(VehiculoRepository vehiculosRepository) {
        this.vehiculoRepository = vehiculosRepository;
    }

    /**
     * Busca vehículos por su marca, ignorando mayúsculas y minúsculas.
     *
     * @param marca la marca del vehículo (por ejemplo, "Toyota").
     * @return lista de vehículos que coinciden con la marca.
     */
    public List<Vehiculo> buscarPorMarca(String marca) {
        return vehiculoRepository.findByMarcaIgnoreCase(marca);
    }

    /**
     * Busca vehículos por su tipo (por ejemplo, "SUV", "Sedán").
     *
     * @param tipo el tipo de vehículo.
     * @return lista de vehículos del tipo especificado.
     */
    public List<Vehiculo> buscarPorTipo(String tipo) {
        return vehiculoRepository.findByTipo(tipo);
    }

    /**
     * Busca vehículos cuyo precio esté dentro del rango especificado.
     *
     * @param min precio mínimo.
     * @param max precio máximo.
     * @return lista de vehículos con precio entre {@code min} y {@code max}.
     */
    public List<Vehiculo> buscarPorRangoDePrecio(Integer min, Integer max) {
        return vehiculoRepository.findByPrecioBetween(min, max);
    }

    /**
     * Realiza una búsqueda combinada por marca, tipo y rango de precios.
     * Si algún parámetro es {@code null}, se ignora en la búsqueda.
     *
     * @param marca la marca del vehículo (puede ser {@code null}).
     * @param tipo el tipo de vehículo (puede ser {@code null}).
     * @param min precio mínimo (puede ser {@code null}).
     * @param max precio máximo (puede ser {@code null}).
     * @return lista de vehículos que cumplen los filtros especificados.
     */
    public List<Vehiculo> buscarVehiculos(String marca, String tipo, Integer min, Integer max) {
        return vehiculoRepository.buscarVehiculos(marca, tipo, min, max);
    }

    /**
     * Obtiene un vehículo por su ID.
     *
     * @param id el ID del vehículo.
     * @return el vehículo correspondiente.
     * @throws RuntimeException si no se encuentra el vehículo.
     */
    public Vehiculo obtenerVehiculoPorId(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));
    }
}