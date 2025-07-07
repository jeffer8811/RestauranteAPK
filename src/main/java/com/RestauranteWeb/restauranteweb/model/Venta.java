package com.RestauranteWeb.restauranteweb.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private double total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<ItemVenta> items;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }



    public List<ItemVenta> getItems() {
        return items;
    }

public void setItems(List<ItemVenta> items) {
    this.items = items;
    for (ItemVenta item : items) {
        item.setVenta(this); // Asocia cada item con esta venta
    }
}

public void setTotal(double total) {
    this.total = total;
}

}
