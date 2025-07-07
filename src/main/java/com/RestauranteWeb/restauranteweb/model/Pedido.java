package com.RestauranteWeb.restauranteweb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    // Se guarda como texto (no como ordinal) en la base de datos
    @Enumerated(EnumType.STRING)
    @Column(length = 20) // ← Asegura espacio suficiente para valores como "EN_PROCESO"
    private EstadoPedido estado;

    // Relación con Mesa
    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    // Relación con los ítems del pedido
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenta> items = new ArrayList<>();

    // Constructor por defecto
    public Pedido() {
        this.fecha = LocalDateTime.now();
        this.estado = EstadoPedido.EN_PROCESO;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public List<ItemVenta> getItems() {
        return items;
    }

    public void setItems(List<ItemVenta> items) {
        this.items = items;
    }
}
