package com.RestauranteWeb.restauranteweb.controller;

import com.RestauranteWeb.restauranteweb.model.Mesa;
import com.RestauranteWeb.restauranteweb.model.Pedido;
import com.RestauranteWeb.restauranteweb.service.MenuItemService;
import com.RestauranteWeb.restauranteweb.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @Autowired
    private MenuItemService menuItemService;

    // ✅ Vista principal con listado de mesas + productos del menú
    @GetMapping
    public String mostrarMesas(Model model) {
        model.addAttribute("mesas", mesaService.obtenerTodasLasMesas());
        model.addAttribute("menuItems", menuItemService.obtenerTodosLosItems()); // ✅ Clave para el select
        return "mesas";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevaMesa(Model model) {
        model.addAttribute("mesa", new Mesa());
        return "form_mesa";
    }

    @PostMapping
    public String guardarMesa(@ModelAttribute Mesa mesa) {
        mesaService.guardarMesa(mesa);
        return "redirect:/mesas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarMesa(@PathVariable Long id, Model model) {
        Optional<Mesa> mesa = mesaService.buscarPorId(id);
        if (mesa.isPresent()) {
            model.addAttribute("mesa", mesa.get());
            return "form_mesa";
        }
        return "redirect:/mesas";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarMesa(@PathVariable Long id, @ModelAttribute Mesa mesa) {
        mesa.setId(id);
        mesaService.guardarMesa(mesa);
        return "redirect:/mesas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMesa(@PathVariable Long id) {
        mesaService.eliminarMesa(id);
        return "redirect:/mesas";
    }

    @PostMapping("/estado/{id}")
    public String cambiarEstado(@PathVariable Long id,
                                 @RequestParam String estado,
                                 RedirectAttributes redirectAttributes) {
        Mesa mesa = mesaService.actualizarEstado(id, estado);
        if (mesa != null && estado.equalsIgnoreCase("Disponible")) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "✅ Mesa #" + mesa.getNumero() + " fue liberada y los meseros fueron notificados.");
        }
        return "redirect:/mesas";
    }

    // ✅ Mostrar formulario para registrar pedido de una mesa específica
    @GetMapping("/mesa/{id}")
    public String mostrarFormularioPedidoPorMesa(@PathVariable Long id, Model model) {
        Pedido pedido = new Pedido();
        Mesa mesa = mesaService.buscarPorId(id).orElse(null);

        if (mesa == null) {
            return "redirect:/mesas";
        }

        pedido.setMesa(mesa);
        model.addAttribute("pedido", pedido);
        model.addAttribute("mesa", mesa);
        model.addAttribute("menuItems", menuItemService.obtenerTodosLosItems()); // Menú para el select

        return "form_pedido_items";
    }
}
