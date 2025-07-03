package com.RestauranteWeb.restauranteweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.RestauranteWeb.restauranteweb.model.Producto;
import com.RestauranteWeb.restauranteweb.repository.ProductoRepository;

@Controller
public class InventarioController {

    @Autowired
    private ProductoRepository productoRepo;

    // Muestra la lista de productos y el formulario en la misma vista
    @GetMapping("/inventario")
    public String mostrarInventario(Model model) {
        List<Producto> productos = productoRepo.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("producto", new Producto()); // para el formulario
        return "inventario"; // plantilla inventario.html
    }

    // Guarda un nuevo producto desde el formulario
    @PostMapping("/producto/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoRepo.save(producto);
        return "redirect:/inventario";
    }

    // Carga datos del producto para editar
    @GetMapping("/producto/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        return productoRepo.findById(id).map(producto -> {
            model.addAttribute("producto", producto);
            model.addAttribute("productos", productoRepo.findAll()); // también carga la tabla
            return "inventario"; // usa la misma vista
        }).orElse("redirect:/inventario"); // si no encuentra el producto, redirige
    }
}
