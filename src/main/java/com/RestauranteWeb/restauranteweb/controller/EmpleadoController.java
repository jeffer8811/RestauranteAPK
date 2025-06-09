package com.RestauranteWeb.restauranteweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.RestauranteWeb.restauranteweb.model.Empleado;
import com.RestauranteWeb.restauranteweb.service.EmpleadoService;

@Controller
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/empleados")
    public String listarEmpleados(Model model) {
        model.addAttribute("empleados", empleadoService.listarEmpleados());
        model.addAttribute("nuevoEmpleado", new Empleado());
        return "empleados";
    }

    @PostMapping("/empleados")
    public String guardarEmpleado(@ModelAttribute("nuevoEmpleado") Empleado empleado) {
        empleado.setEstado("Activo");
        empleadoService.guardar(empleado);
        return "redirect:/empleados";
    }

    @GetMapping("/empleados/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Empleado empleado = empleadoService.buscarPorId(id);
        model.addAttribute("empleado", empleado);
        return "empleados/editarEmpleado"; // ¡Debe coincidir con el nombre del archivo y la carpeta!
    }

    @PostMapping("/empleados/actualizar")
    public String actualizarEmpleado(@ModelAttribute Empleado empleado) {
        empleadoService.guardar(empleado); // este método actualiza si el ID ya existe
        return "redirect:/empleados";
    }

}
