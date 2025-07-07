package com.RestauranteWeb.restauranteweb.repository;

import com.RestauranteWeb.restauranteweb.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocionRepository extends JpaRepository<Promocion, Long> {
}