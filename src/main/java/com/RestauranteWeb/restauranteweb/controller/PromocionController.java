package com.RestauranteWeb.restauranteweb.controller;

import com.RestauranteWeb.restauranteweb.model.Promocion;
import com.RestauranteWeb.restauranteweb.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PromocionController {

    @Autowired
    private PromocionRepository promocionRepository;

    // Muestra la página principal de promociones
    @GetMapping("/promociones")
    public String listarPromociones(Model model) {
        model.addAttribute("promociones", promocionRepository.findAll());
        return "promociones"; // Llama a promociones.html
    }

    // Muestra el formulario para crear una nueva promoción
    @GetMapping("/promociones/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("promocion", new Promocion());
        return "crear-promocion";
    }

    // Procesa el formulario y guarda la nueva promoción
    @PostMapping("/promociones/guardar")
    public String guardarPromocion(@ModelAttribute Promocion promocion) {
        promocion.setActivo(true);
        promocionRepository.save(promocion);
        return "redirect:/promociones";
    }

    // Muestra el formulario para editar una promoción existente
    @GetMapping("/promociones/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Promocion promocion = promocionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de Promoción inválido: " + id));

        model.addAttribute("promocion", promocion);
        return "crear-promocion";
    }

    // ✅ NUEVO: Elimina una promoción por su ID
    @GetMapping("/promociones/eliminar/{id}")
    public String eliminarPromocion(@PathVariable Long id) {
        Promocion promocion = promocionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de Promoción inválido: " + id));
        
        promocionRepository.delete(promocion);
        return "redirect:/promociones";
    }
}
