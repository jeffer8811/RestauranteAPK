package com.RestauranteWeb.restauranteweb.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.RestauranteWeb.restauranteweb.model.MenuItem;

public interface MenuItemService {
    List<MenuItem> obtenerTodosLosItems();
    List<MenuItem> obtenerItemsPorCategoria(String categoria);
    MenuItem obtenerItemPorId(Long id);
    MenuItem guardarItem(MenuItem item); // si quieres seguir teniendo la versión simple
    MenuItem guardarItem(MenuItem item, MultipartFile imagen); // con imagen
    MenuItem actualizarItem(Long id, MenuItem item, MultipartFile imagen);
    void eliminarItem(Long id);
    void cambiarDisponibilidad(Long id, boolean disponible);
    List<String> obtenerCategoriasDisponibles();
}
