package com.concesionario.ventacar.Controller;

import com.concesionario.ventacar.Model.Vehiculo;
import com.concesionario.ventacar.Service.VehiculoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")

public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping("/buscar")
    public List<Vehiculo> buscarVehiculos(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {
        return vehiculoService.buscarVehiculos(marca, tipo, min, max);
    }



    @GetMapping("/marca/{marca}")
    public List<Vehiculo> buscarPorMarca(@PathVariable String marca) {
        return vehiculoService.buscarPorMarca(marca);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Vehiculo> buscarPorTipo(@PathVariable String tipo) {
        return vehiculoService.buscarPorTipo(tipo);
    }

    @GetMapping("/precio")
    public List<Vehiculo> buscarPorRangoDePrecio(@RequestParam Integer min, @RequestParam Integer max) {
        return vehiculoService.buscarPorRangoDePrecio(min, max);
    }

    @GetMapping("/{id}")
    public Vehiculo obtenerVehiculoPorId(@PathVariable Long id) {
        return vehiculoService.obtenerVehiculoPorId(id);
    }
}
