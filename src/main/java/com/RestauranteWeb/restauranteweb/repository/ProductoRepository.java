package com.RestauranteWeb.restauranteweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.RestauranteWeb.restauranteweb.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
