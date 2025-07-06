package com.RestauranteWeb.restauranteweb.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.RestauranteWeb.restauranteweb.model.MenuItem;
import com.RestauranteWeb.restauranteweb.service.MenuItemService;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public String gestionMenu(Model model) {
        List<MenuItem> menuItems = menuItemService.obtenerTodosLosItems();
        model.addAttribute("menuItems", menuItems);
        return "menu";
    }

    @PostMapping("/save")
    public String guardarItem(@RequestParam(value = "id", required = false) Long id,
                              @RequestParam("nombre") String nombre,
                              @RequestParam("descripcion") String descripcion,
                              @RequestParam("categoria") String categoria,
                              @RequestParam("precio") String precio,
                              @RequestParam(value = "disponible", defaultValue = "false") boolean disponible,
                              @RequestParam("imagen") MultipartFile imagen) {
        try {
            MenuItem item = (id != null) ? menuItemService.obtenerItemPorId(id) : new MenuItem();
            item.setNombre(nombre);
            item.setDescripcion(descripcion);
            item.setCategoria(categoria);
            item.setPrecio(new BigDecimal(precio));
            item.setDisponible(disponible);

            menuItemService.guardarItem(item, imagen); // <-- Cambiado aquí

            return "redirect:/menu?success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/menu?error";
        }
    }

    @GetMapping("/delete/{id}")
    public String eliminarItem(@PathVariable Long id) {
        menuItemService.eliminarItem(id);
        return "redirect:/menu?deleted";
    }
}
