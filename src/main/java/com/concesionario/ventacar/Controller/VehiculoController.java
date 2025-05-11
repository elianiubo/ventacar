package com.concesionario.ventacar.Controller;

import com.concesionario.ventacar.Model.Vehiculo;
import com.concesionario.ventacar.Service.VehiculoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con los vehículos.
 * Contiene los endpoints necesarios para buscar vehículos por distintos criterios,
 * como marca, tipo, precio y obtener detalles de un vehículo por su ID.
 */
@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")  // Permite solicitudes desde cualquier origen (CORS)
public class VehiculoController {

    /**
     * Servicio encargado de gestionar las operaciones sobre los vehículos.
     */
    private final VehiculoService vehiculoService;

    /**
     * Constructor que inyecta el servicio de vehículos.
     *
     * @param vehiculoService el servicio de vehículos a inyectar.
     */
    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    /**
     * Endpoint para buscar vehículos según diversos parámetros de filtro.
     * Se pueden especificar la marca, tipo, precio mínimo y precio máximo de los vehículos.
     *
     * @param marca la marca del vehículo a buscar (opcional).
     * @param tipo el tipo de vehículo a buscar (opcional).
     * @param min el precio mínimo de los vehículos a buscar (opcional).
     * @param max el precio máximo de los vehículos a buscar (opcional).
     * @return una lista de vehículos que coinciden con los filtros especificados.
     */
    @GetMapping("/buscar")
    public List<Vehiculo> buscarVehiculos(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {
        return vehiculoService.buscarVehiculos(marca, tipo, min, max);
    }

    /**
     * Endpoint para buscar vehículos por marca.
     *
     * @param marca la marca de los vehículos a buscar.
     * @return una lista de vehículos que coinciden con la marca especificada.
     */
    @GetMapping("/marca/{marca}")
    public List<Vehiculo> buscarPorMarca(@PathVariable String marca) {
        return vehiculoService.buscarPorMarca(marca);
    }

    /**
     * Endpoint para buscar vehículos por tipo.
     *
     * @param tipo el tipo de vehículo a buscar (por ejemplo, SUV, sedán, etc.).
     * @return una lista de vehículos que coinciden con el tipo especificado.
     */
    @GetMapping("/tipo/{tipo}")
    public List<Vehiculo> buscarPorTipo(@PathVariable String tipo) {
        return vehiculoService.buscarPorTipo(tipo);
    }

    /**
     * Endpoint para buscar vehículos dentro de un rango de precios.
     *
     * @param min el precio mínimo de los vehículos a buscar.
     * @param max el precio máximo de los vehículos a buscar.
     * @return una lista de vehículos que caen dentro del rango de precios especificado.
     */
    @GetMapping("/precio")
    public List<Vehiculo> buscarPorRangoDePrecio(@RequestParam Integer min, @RequestParam Integer max) {
        return vehiculoService.buscarPorRangoDePrecio(min, max);
    }

    /**
     * Endpoint para obtener los detalles de un vehículo por su ID.
     *
     * @param id el ID del vehículo a obtener.
     * @return los detalles del vehículo con el ID especificado.
     */
    @GetMapping("/{id}")
    public Vehiculo obtenerVehiculoPorId(@PathVariable Long id) {
        return vehiculoService.obtenerVehiculoPorId(id);
    }
}