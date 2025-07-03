package com.RestauranteWeb.restauranteweb.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;
    private String nombre;
    private String unidad;

    private int stockActual;
    private int stockMinimo;

    private Double precioUnitario;

    private LocalDate fechaUltimaEntrada;

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public int getStockActual() { return stockActual; }
    public void setStockActual(int stockActual) { this.stockActual = stockActual; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }

    public LocalDate getFechaUltimaEntrada() { return fechaUltimaEntrada; }
    public void setFechaUltimaEntrada(LocalDate fechaUltimaEntrada) { this.fechaUltimaEntrada = fechaUltimaEntrada; }
}
