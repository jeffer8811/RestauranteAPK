package com.RestauranteWeb.restauranteweb.controller;

import com.RestauranteWeb.restauranteweb.model.ItemVenta;
import com.RestauranteWeb.restauranteweb.model.MenuItem;
import com.RestauranteWeb.restauranteweb.model.Venta;
import com.RestauranteWeb.restauranteweb.repository.ItemVentaRepository;
import com.RestauranteWeb.restauranteweb.repository.MenuItemRepository;
import com.RestauranteWeb.restauranteweb.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private MenuItemRepository menuItemRepo;

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private ItemVentaRepository itemVentaRepo;

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("menuItems", menuItemRepo.findAll());
        model.addAttribute("ventas", ventaRepo.findAll()); // necesario para listar
        return "formulario-venta";
    }

    @PostMapping("/guardar")
    public String guardarVenta(@RequestParam LocalDate fecha,
            @RequestParam Long menuItemId,
            @RequestParam int cantidad) {

        MenuItem item = menuItemRepo.findById(menuItemId).orElseThrow();

        Venta venta = new Venta();
        venta.setFecha(fecha);
        venta.setTotal(item.getPrecio().doubleValue() * cantidad);
        ventaRepo.save(venta);

        ItemVenta itemVenta = new ItemVenta();
        itemVenta.setVenta(venta);
        itemVenta.setMenuItem(item);
        itemVenta.setCantidad(cantidad);
        itemVentaRepo.save(itemVenta);

        return "redirect:/ventas/nueva"; // redirige al formulario y listado
    }

    @GetMapping
    public String verVentas(Model model) {
        model.addAttribute("ventas", ventaRepo.findAll());
        model.addAttribute("menuItems", menuItemRepo.findAll());
        return "ventas";
    }
}
