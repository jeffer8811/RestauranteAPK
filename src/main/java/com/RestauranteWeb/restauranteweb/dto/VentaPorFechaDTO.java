package com.RestauranteWeb.restauranteweb.dto;

public class VentaPorFechaDTO {
    private String fecha;
    private double total;

    public VentaPorFechaDTO(String fecha, double total) {
        this.fecha = fecha;
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }
}
