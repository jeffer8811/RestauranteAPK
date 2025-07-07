package com.RestauranteWeb.restauranteweb.controller;

import com.RestauranteWeb.restauranteweb.model.EstadoPedido;
import com.RestauranteWeb.restauranteweb.model.Mesa;
import com.RestauranteWeb.restauranteweb.model.Pedido;
import com.RestauranteWeb.restauranteweb.service.MesaService;
import com.RestauranteWeb.restauranteweb.service.PedidoService;
import com.RestauranteWeb.restauranteweb.service.VentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/cobros")
public class CobroController {

    @Autowired
    private MesaService mesaService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private VentaService ventaService; // ✅ Agregado correctamente

    // Mostrar todas las mesas y sus totales
    @GetMapping
    public String mostrarCobros(Model model) {
        List<Mesa> mesas = mesaService.listarMesas();
        model.addAttribute("mesas", mesas);

        Map<Long, Double> totales = new HashMap<>();
        for (Mesa mesa : mesas) {
            double total = pedidoService.calcularTotalPorMesa(mesa.getId()).doubleValue();
            totales.put(mesa.getId(), total);
        }

        model.addAttribute("totales", totales);
        return "cobros";
    }

    @PostMapping("/realizar")
    public String cobrarMesa(@RequestParam("mesaId") Long mesaId) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorMesa(mesaId);

        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() == EstadoPedido.LISTO || pedido.getEstado() == EstadoPedido.EN_PROCESO) {
                ventaService.registrarVentaDesdePedido(pedido);
            }
        }

        // ✅ Eliminar los pedidos de la mesa después del cobro
        pedidoService.eliminarPedidosPorMesa(mesaId);

        // ✅ Cambiar estado de la mesa a disponible
        mesaService.actualizarEstado(mesaId, "Disponible");

        return "redirect:/cobros";
    }

}
