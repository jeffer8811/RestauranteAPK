package com.RestauranteWeb.restauranteweb.controller;

import com.RestauranteWeb.restauranteweb.model.Promocion;
import com.RestauranteWeb.restauranteweb.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        return "crear-promocion"; // Llama a crear-promocion.html
    }
    
    // Procesa el formulario y guarda la nueva promoción
    @PostMapping("/promociones/guardar")
    public String guardarPromocion(@ModelAttribute Promocion promocion) {
        // Por defecto la ponemos activa al crearla
        promocion.setActivo(true);
        promocionRepository.save(promocion);
        return "redirect:/promociones"; // Redirige a la lista de promociones
    }
}