package com.RestauranteWeb.restauranteweb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;
    private String rol;
    private String correo;
    private String dni;
    private String estado; // Para "Activo" o "Inactivo"

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}