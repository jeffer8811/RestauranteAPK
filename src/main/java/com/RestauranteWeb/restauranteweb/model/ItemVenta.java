package com.RestauranteWeb.restauranteweb.model;

import jakarta.persistence.*;

@Entity
public class ItemVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Venta
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    // Relación con MenuItem
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    // Relación con Pedido (si aplica)
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private String nombre;

    private double precioUnitario;

    private int cantidad;

    // Constructor vacío
    public ItemVenta() {
    }

    // Constructor con parámetros
    public ItemVenta(Venta venta, MenuItem menuItem, int cantidad) {
        this.venta = venta;
        this.menuItem = menuItem;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
}