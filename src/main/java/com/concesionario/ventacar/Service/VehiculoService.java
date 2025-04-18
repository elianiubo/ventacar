package com.concesionario.ventacar.Service;

import com.concesionario.ventacar.Model.Vehiculo;
import com.concesionario.ventacar.Repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculosRepository) {
        this.vehiculoRepository = vehiculosRepository;
    }

    public List<Vehiculo> buscarPorMarca(String marca) {
        return vehiculoRepository.findByMarcaIgnoreCase(marca);
    }

    public List<Vehiculo> buscarPorTipo(String tipo) {
        return vehiculoRepository.findByTipo(tipo);
    }

    public List<Vehiculo> buscarPorRangoDePrecio(Integer min, Integer max) {
        return vehiculoRepository.findByPrecioBetween(min, max);
    }

    public List<Vehiculo> buscarVehiculos(String marca, String tipo, Integer min, Integer max) {
        return vehiculoRepository.buscarVehiculos(marca, tipo, min, max);
    }

}
