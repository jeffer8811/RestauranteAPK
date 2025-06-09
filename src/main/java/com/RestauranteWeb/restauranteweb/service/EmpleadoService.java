package com.RestauranteWeb.restauranteweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RestauranteWeb.restauranteweb.model.Empleado;
import com.RestauranteWeb.restauranteweb.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Listar todos los empleados
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    // Guardar (crear o actualizar) un empleado
    public void guardar(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    // Buscar un empleado por su ID
    public Empleado buscarPorId(Long id) {
        return empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
    }

    // Método setEstado (no se usa de momento)
    public void setEstado(String string) {
        throw new UnsupportedOperationException("Método 'setEstado' aún no implementado");
    }
}
