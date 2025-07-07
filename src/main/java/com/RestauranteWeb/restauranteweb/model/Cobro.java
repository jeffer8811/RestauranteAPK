package com.RestauranteWeb.restauranteweb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cobro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double total;

    private LocalDateTime fecha;

    @OneToOne
    private Mesa mesa;

    // Constructores
    public Cobro() {}

    public Cobro(double total, LocalDateTime fecha, Mesa mesa) {
        this.total = total;
        this.fecha = fecha;
        this.mesa = mesa;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
}
