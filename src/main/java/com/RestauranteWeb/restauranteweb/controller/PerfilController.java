package com.RestauranteWeb.restauranteweb.controller;

import com.RestauranteWeb.restauranteweb.model.Perfil;
import com.RestauranteWeb.restauranteweb.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    // Muestra la lista de perfiles
    @GetMapping
    public String listarPerfiles(Model model) {
        model.addAttribute("perfiles", perfilRepository.findAll());
        return "gestion-perfiles";
    }

    // Muestra el formulario para AÑADIR un nuevo perfil
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("perfil", new Perfil());
        model.addAttribute("pageTitle", "Añadir Perfil");
        return "form-perfil";
    }

    // Muestra el formulario para EDITAR un perfil existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id de Perfil inválido:" + id));
        model.addAttribute("perfil", perfil);
        model.addAttribute("pageTitle", "Editar perfil de usuario:");
        return "form-perfil";
    }

    // Procesa el GUARDADO (tanto para nuevos como para editados)
    @PostMapping("/guardar")
    public String guardarOActualizar(@ModelAttribute Perfil perfil) {
        if (perfil.getId() == null) {
            perfil.setEstado("Activo"); // Asigna 'Activo' solo si es nuevo
        }
        perfilRepository.save(perfil);
        return "redirect:/perfiles";
    }

    // Procesa la DESACTIVACIÓN
    @PostMapping("/desactivar/{id}")
    public String desactivarPerfil(@PathVariable Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id de Perfil inválido:" + id));
        perfil.setEstado("Inactivo");
        perfilRepository.save(perfil);
        return "redirect:/perfiles";
    }
}