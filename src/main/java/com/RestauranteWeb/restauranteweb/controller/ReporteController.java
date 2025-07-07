package com.RestauranteWeb.restauranteweb.controller;

import com.RestauranteWeb.restauranteweb.dto.MenuMasVendidoDTO;
import com.RestauranteWeb.restauranteweb.dto.ReporteResumen;
import com.RestauranteWeb.restauranteweb.dto.VentaPorFechaDTO;
import com.RestauranteWeb.restauranteweb.dto.VentaDetalleDTO; // ✅ IMPORTANTE
import com.RestauranteWeb.restauranteweb.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public String mostrarFormularioReportes(Model model) {
        model.addAttribute("desde", LocalDate.now().minusDays(7));
        model.addAttribute("hasta", LocalDate.now());
        return "reportes";
    }

    @PostMapping
    public String generarReporte(@RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                 @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
                                 Model model) {

        // 1. Resumen general
        ReporteResumen resumen = reporteService.generarResumen(desde, hasta);

        // 2. Datos para el gráfico de ventas por fecha
        List<VentaPorFechaDTO> ventasPorFecha = reporteService.obtenerVentasPorFecha(desde, hasta);

        // 3. Menús más vendidos
        List<MenuMasVendidoDTO> menusMasVendidos = reporteService.obtenerMenusMasVendidos(desde, hasta);

        // ✅ 4. Ventas detalle
        List<VentaDetalleDTO> ventasDetalle = reporteService.obtenerVentasDetalle(desde, hasta);

        // Añadir al modelo
        model.addAttribute("resumen", resumen);
        model.addAttribute("ventasPorFecha", ventasPorFecha);
        model.addAttribute("menusMasVendidos", menusMasVendidos);
        model.addAttribute("ventasDetalle", ventasDetalle);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        return "reportes";
    }
}
