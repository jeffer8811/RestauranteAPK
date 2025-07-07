package com.RestauranteWeb.restauranteweb.repository;

import com.RestauranteWeb.restauranteweb.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByFechaBetween(LocalDate desde, LocalDate hasta);
    
}
