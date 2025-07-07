package com.RestauranteWeb.restauranteweb.service;

import com.RestauranteWeb.restauranteweb.model.Mesa;
import com.RestauranteWeb.restauranteweb.observer.Mesero;
import com.RestauranteWeb.restauranteweb.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    // ✅ Método para listar todas las mesas (útil para pedidos)
    public List<Mesa> obtenerTodasLasMesas() {
        return mesaRepository.findAll();
    }

    // Guardar o registrar nueva mesa
    public Mesa guardarMesa(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    // Buscar mesa por ID
    public Optional<Mesa> buscarPorId(Long id) {
        return mesaRepository.findById(id);
    }

    // Eliminar mesa
    public void eliminarMesa(Long id) {
        mesaRepository.deleteById(id);
    }

    // Actualizar estado y notificar a meseros si es necesario
    public Mesa actualizarEstado(Long id, String estado) {
        Optional<Mesa> mesaOpt = mesaRepository.findById(id);
        if (mesaOpt.isPresent()) {
            Mesa mesa = mesaOpt.get();

            if (estado.equalsIgnoreCase("Disponible")) {
                // Patrón Observer: notifica cuando queda libre
                Mesero mesero1 = new Mesero("Carlos");
                Mesero mesero2 = new Mesero("Lucía");

                mesa.agregarObservador(mesero1);
                mesa.agregarObservador(mesero2);
            }

            mesa.setEstado(estado); // esto dispara la notificación
            return mesaRepository.save(mesa);
        }
        return null;
    }
public List<Mesa> listarMesas() {
    return mesaRepository.findAll();
}

}
