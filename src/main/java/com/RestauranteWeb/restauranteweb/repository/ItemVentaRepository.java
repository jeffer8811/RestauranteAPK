package com.RestauranteWeb.restauranteweb.repository;

import com.RestauranteWeb.restauranteweb.dto.MenuMasVendidoDTO;
import com.RestauranteWeb.restauranteweb.model.ItemVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ItemVentaRepository extends JpaRepository<ItemVenta, Long> {

    @Query("SELECT new com.RestauranteWeb.restauranteweb.dto.MenuMasVendidoDTO(i.menuItem.nombre, SUM(i.cantidad)) " +
           "FROM ItemVenta i " +
           "WHERE i.venta.fecha BETWEEN :desde AND :hasta " +
           "GROUP BY i.menuItem.nombre ORDER BY SUM(i.cantidad) DESC")
    List<MenuMasVendidoDTO> obtenerMenusMasVendidos(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);
}
