package com.RestauranteWeb.restauranteweb.service;

import com.RestauranteWeb.restauranteweb.model.EstadoPedido;
import com.RestauranteWeb.restauranteweb.model.ItemVenta;
import com.RestauranteWeb.restauranteweb.model.Mesa;
import com.RestauranteWeb.restauranteweb.model.Pedido;
import com.RestauranteWeb.restauranteweb.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MesaService mesaService;

    // ✅ Registrar un nuevo pedido
    public Pedido registrarPedido(Pedido pedido) {
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.EN_PROCESO);
        return pedidoRepository.save(pedido);
    }

    // ✅ Obtener todos los pedidos
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    // ✅ Obtener todos los pedidos por mesa
    public List<Pedido> obtenerPedidosPorMesa(Long mesaId) {
        return pedidoRepository.findByMesaId(mesaId);
    }

    // ✅ Actualizar el estado de un pedido
    public Pedido actualizarEstado(Long pedidoId, EstadoPedido nuevoEstado) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setEstado(nuevoEstado);
            return pedidoRepository.save(pedido);
        }
        return null;
    }

    // ✅ Calcular el total de todos los pedidos de una mesa
    public BigDecimal calcularTotalPorMesa(Long mesaId) {
        List<Pedido> pedidos = pedidoRepository.findByMesaId(mesaId);
        BigDecimal total = BigDecimal.ZERO;

        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() == EstadoPedido.LISTO ||
                pedido.getEstado() == EstadoPedido.ENTREGADO ||
                pedido.getEstado() == EstadoPedido.EN_PROCESO) {

                List<ItemVenta> items = pedido.getItems(); // ✅ Asegúrate que Pedido tenga getItems()
                if (items != null) {
                    for (ItemVenta item : items) {
                        BigDecimal precio = BigDecimal.valueOf(item.getPrecioUnitario());
                        BigDecimal cantidad = BigDecimal.valueOf(item.getCantidad());
                        total = total.add(precio.multiply(cantidad));
                    }
                }
            }
        }

        return total;
    }

    // ✅ Obtener un pedido EN_PROCESO o crear uno nuevo
    public Pedido obtenerOcrearPedidoEnProceso(Long mesaId) {
        Optional<Pedido> pedidoExistente =
                pedidoRepository.findByMesaIdAndEstado(mesaId, EstadoPedido.EN_PROCESO);

        if (pedidoExistente.isPresent()) {
            return pedidoExistente.get();
        }

        Mesa mesa = mesaService.buscarPorId(mesaId).orElseThrow(
                () -> new RuntimeException("Mesa no encontrada con ID: " + mesaId)
        );

        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setMesa(mesa);
        nuevoPedido.setFecha(LocalDateTime.now());
        nuevoPedido.setEstado(EstadoPedido.EN_PROCESO);

        return pedidoRepository.save(nuevoPedido);
    }

    // ✅ Guardar un pedido (nuevo o actualizado)
    public void guardarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }
    public void eliminarPedido(Long id) {
    pedidoRepository.deleteById(id);
}
public void eliminarPedidosPorMesa(Long mesaId) {
    List<Pedido> pedidos = pedidoRepository.findByMesaId(mesaId);
    pedidoRepository.deleteAll(pedidos);
}

}
