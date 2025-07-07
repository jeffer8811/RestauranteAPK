package com.RestauranteWeb.restauranteweb.dto;

public class MenuMasVendidoDTO {
    private String nombre;
    private long cantidad;

    public MenuMasVendidoDTO(String nombre, long cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public long getCantidad() {
        return cantidad;
    }
}
