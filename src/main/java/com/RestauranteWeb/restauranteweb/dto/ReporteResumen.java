package com.RestauranteWeb.restauranteweb.dto;


public class ReporteResumen {

    private double totalVentas;
    private long cantidadVentas;

    // Constructor
    public ReporteResumen(double totalVentas, long cantidadVentas) {
        this.totalVentas = totalVentas;
        this.cantidadVentas = cantidadVentas;
    }

    // Getters y Setters
    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public long getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(long cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }
}
