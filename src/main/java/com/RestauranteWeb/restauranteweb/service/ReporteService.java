package com.RestauranteWeb.restauranteweb.service;

import com.RestauranteWeb.restauranteweb.dto.MenuMasVendidoDTO;
import com.RestauranteWeb.restauranteweb.dto.ReporteResumen;
import com.RestauranteWeb.restauranteweb.dto.VentaDetalleDTO;
import com.RestauranteWeb.restauranteweb.dto.VentaPorFechaDTO;
import com.RestauranteWeb.restauranteweb.model.Venta;
import com.RestauranteWeb.restauranteweb.repository.ItemVentaRepository;
import com.RestauranteWeb.restauranteweb.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ItemVentaRepository itemVentaRepository;

    // 1. Resumen total
    public ReporteResumen generarResumen(LocalDate desde, LocalDate hasta) {
        List<Venta> ventas = ventaRepository.findByFechaBetween(desde, hasta);

        double totalVentas = ventas.stream()
                .mapToDouble(Venta::getTotal)
                .sum();

        long cantidadVentas = ventas.size();

        return new ReporteResumen(totalVentas, cantidadVentas);
    }

    // 2. Ventas por fecha para el gráfico
    public List<VentaPorFechaDTO> obtenerVentasPorFecha(LocalDate desde, LocalDate hasta) {
        List<Venta> ventas = ventaRepository.findByFechaBetween(desde, hasta);

        return ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getFecha().toString(), // Agrupar por fecha como String
                        Collectors.summingDouble(Venta::getTotal)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Ordenar por fecha ascendente
                .map(entry -> new VentaPorFechaDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    // 3. Menús más vendidos
    public List<MenuMasVendidoDTO> obtenerMenusMasVendidos(LocalDate desde, LocalDate hasta) {
        return itemVentaRepository.obtenerMenusMasVendidos(desde, hasta);
    }
    public List<VentaDetalleDTO> obtenerVentasDetalle(LocalDate desde, LocalDate hasta) {
    return ventaRepository.findByFechaBetween(desde, hasta).stream()
        .map(v -> new VentaDetalleDTO(v.getFecha().toString(), v.getTotal()))
        .collect(Collectors.toList());
}

}
