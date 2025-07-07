package com.RestauranteWeb.restauranteweb.observer;

public class Mesero implements Observador {

    private String nombre;

    public Mesero(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void actualizar(String mensaje) {
        System.out.println("📢 Notificación para " + nombre + ": " + mensaje);
    }
}
// Este código define la clase Mesero que implementa la interfaz Observador.
// Cuando se recibe una notificación, imprime un mensaje en la consola con el nombre del mesero y el mensaje recibido.
// Esto permite que los meseros reciban actualizaciones sobre el estado de las mesas, como cuando una mesa se vuelve disponible. 