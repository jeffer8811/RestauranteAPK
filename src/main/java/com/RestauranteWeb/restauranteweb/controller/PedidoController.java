package com.RestauranteWeb.restauranteweb.controller;

import com.RestauranteWeb.restauranteweb.model.*;
import com.RestauranteWeb.restauranteweb.service.MenuItemService;
import com.RestauranteWeb.restauranteweb.service.MesaService;
import com.RestauranteWeb.restauranteweb.service.PedidoService;
import com.RestauranteWeb.restauranteweb.service.VentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private MesaService mesaService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private VentaService ventaService;

    // Vista principal: formulario + tabla
    @GetMapping
    public String mostrarVistaPedidos(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("mesas", mesaService.obtenerTodasLasMesas());
        model.addAttribute("pedidos", pedidoService.obtenerTodos());
        model.addAttribute("estados", EstadoPedido.values());
        return "pedidos";
    }

    // Registrar nuevo pedido
    @PostMapping("/registrar")
    public String registrarPedido(@ModelAttribute Pedido pedido) {
        pedidoService.registrarPedido(pedido);
        return "redirect:/pedidos";
    }

    // Actualizar estado del pedido
    @PostMapping("/actualizarEstado")
    public String actualizarEstado(@RequestParam Long id, @RequestParam EstadoPedido estado) {
        pedidoService.actualizarEstado(id, estado);
        return "redirect:/pedidos";
    }

    @GetMapping("/cobro/{mesaId}")
    public String verCobroPorMesa(@PathVariable Long mesaId, Model model) {
        double total = pedidoService.calcularTotalPorMesa(mesaId).doubleValue();
        Mesa mesa = mesaService.buscarPorId(mesaId).orElse(null);
        List<Pedido> pedidosMesa = pedidoService.obtenerPedidosPorMesa(mesaId);

        model.addAttribute("mesa", mesa);
        model.addAttribute("total", total);
        model.addAttribute("pedidos", pedidosMesa);
        return "cobro-mesa";
    }

    @GetMapping("/mesa/{id}")
    public String mostrarFormularioPedidoPorMesa(@PathVariable Long id, Model model) {
        Pedido pedido = new Pedido();
        Mesa mesa = mesaService.buscarPorId(id).orElse(null);

        if (mesa == null) {
            return "redirect:/mesas";
        }

        pedido.setMesa(mesa);
        model.addAttribute("pedido", pedido);
        return "form_pedido_items";
    }

    @PostMapping("/guardarItem")
    public String guardarItemPedido(@RequestParam Long mesaId,
            @RequestParam Long menuItemId,
            @RequestParam int cantidad) {

        Mesa mesa = mesaService.buscarPorId(mesaId).orElse(null);
        MenuItem menuItem = menuItemService.obtenerItemPorId(menuItemId);

        if (mesa == null || menuItem == null) {
            return "redirect:/mesas?error=true";
        }

        Pedido pedido = pedidoService.obtenerOcrearPedidoEnProceso(mesaId);

        ItemVenta item = new ItemVenta();
        item.setMenuItem(menuItem);
        item.setCantidad(cantidad);
        item.setPrecioUnitario(menuItem.getPrecio().doubleValue()); // ✅ El precio se jala automáticamente
        item.setPedido(pedido);

        pedido.getItems().add(item);
        pedidoService.guardarPedido(pedido);

        return "redirect:/mesas";
    }

    // Este método debería estar en VentaService, pero lo dejamos aquí si es
    // temporal
    public void registrarVentaDesdePedido(Pedido pedido) {
        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());

        List<ItemVenta> itemsVenta = new ArrayList<>();

        for (ItemVenta itemPedido : pedido.getItems()) {
            if (itemPedido.getMenuItem() == null)
                continue;

            ItemVenta itemVenta = new ItemVenta();
            itemVenta.setVenta(venta);
            itemVenta.setMenuItem(itemPedido.getMenuItem());
            itemVenta.setNombre(itemPedido.getMenuItem().getNombre());
            itemVenta.setCantidad(itemPedido.getCantidad());
            itemVenta.setPrecioUnitario(itemPedido.getPrecioUnitario());

            itemsVenta.add(itemVenta);
        }

        venta.setItems(itemsVenta);
        venta.setTotal(calcularTotalVenta(itemsVenta));

        ventaService.guardarVenta(venta);
    }

    private double calcularTotalVenta(List<ItemVenta> itemsVenta) {
        double total = 0;
        for (ItemVenta item : itemsVenta) {
            total += item.getCantidad() * item.getPrecioUnitario();
        }
        return total;
    }
}
