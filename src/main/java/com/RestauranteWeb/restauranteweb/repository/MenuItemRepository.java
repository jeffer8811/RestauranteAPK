package com.RestauranteWeb.restauranteweb.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.RestauranteWeb.restauranteweb.model.MenuItem;

import java.util.List;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoriaIgnoreCase(String categoria);
    List<MenuItem> findByDisponible(boolean disponible);
    List<MenuItem> findByCategoriaIgnoreCaseAndDisponible(String categoria, boolean disponible);
}