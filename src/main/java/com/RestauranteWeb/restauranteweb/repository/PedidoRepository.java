package com.RestauranteWeb.restauranteweb.repository;
import com.RestauranteWeb.restauranteweb.model.EstadoPedido;
import com.RestauranteWeb.restauranteweb.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByMesaId(Long mesaId);

    Optional<Pedido> findByMesaIdAndEstado(Long mesaId, EstadoPedido estado); // 👈 Agrega este método
}
