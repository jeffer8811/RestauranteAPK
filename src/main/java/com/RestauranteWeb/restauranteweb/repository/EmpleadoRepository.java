package com.RestauranteWeb.restauranteweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.RestauranteWeb.restauranteweb.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
