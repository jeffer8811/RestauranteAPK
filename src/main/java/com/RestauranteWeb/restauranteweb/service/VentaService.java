package com.RestauranteWeb.restauranteweb.service;


import com.RestauranteWeb.restauranteweb.model.ItemVenta;
import com.RestauranteWeb.restauranteweb.model.Pedido;
import com.RestauranteWeb.restauranteweb.model.Venta;
import com.RestauranteWeb.restauranteweb.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public void registrarVentaDesdePedido(Pedido pedido) {
        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());

        List<ItemVenta> itemsVenta = new ArrayList<>();

        for (ItemVenta itemPedido : pedido.getItems()) {
            if (itemPedido.getMenuItem() == null) continue;

            ItemVenta itemVenta = new ItemVenta();
            itemVenta.setMenuItem(itemPedido.getMenuItem());
            itemVenta.setNombre(itemPedido.getMenuItem().getNombre());
            itemVenta.setCantidad(itemPedido.getCantidad());
            itemVenta.setPrecioUnitario(itemPedido.getPrecioUnitario());

            itemVenta.setVenta(venta); // ¡IMPORTANTE!

            itemsVenta.add(itemVenta);
        }

        venta.setItems(itemsVenta);
        venta.setTotal(calcularTotalVenta(itemsVenta));

        ventaRepository.save(venta);
    }

    private double calcularTotalVenta(List<ItemVenta> items) {
        double total = 0;
        for (ItemVenta item : items) {
            total += item.getPrecioUnitario() * item.getCantidad();
        }
        return total;
    }

    public void guardarVenta(Venta venta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarVenta'");
    }
}
