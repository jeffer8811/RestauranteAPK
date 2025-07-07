package com.RestauranteWeb.restauranteweb.service;

import com.RestauranteWeb.restauranteweb.model.MenuItem;
import com.RestauranteWeb.restauranteweb.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final Path rootLocation;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
        this.rootLocation = Paths.get("uploads/img/menu");
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio para almacenar imágenes", e);
        }
    }

    @Override
    public List<MenuItem> obtenerTodosLosItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public List<MenuItem> obtenerItemsPorCategoria(String categoria) {
        return menuItemRepository.findByCategoriaIgnoreCase(categoria);
    }

    @Override
    public MenuItem obtenerItemPorId(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ítem del menú no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public MenuItem guardarItem(MenuItem item) {
        return menuItemRepository.save(item);
    }

    @Override
    @Transactional
    public MenuItem guardarItem(MenuItem item, MultipartFile imagen) {
        if (imagen != null && !imagen.isEmpty()) {
            String nombreImagen = guardarImagen(imagen);
            item.setImagen("img/menu/" + nombreImagen);
        }
        return menuItemRepository.save(item);
    }

    @Override
    @Transactional
    public MenuItem actualizarItem(Long id, MenuItem item, MultipartFile imagen) {
        MenuItem itemExistente = obtenerItemPorId(id);

        itemExistente.setNombre(item.getNombre());
        itemExistente.setDescripcion(item.getDescripcion());
        itemExistente.setCategoria(item.getCategoria());
        itemExistente.setPrecio(item.getPrecio());
        itemExistente.setDisponible(item.isDisponible());

        if (imagen != null && !imagen.isEmpty()) {
            String nombreImagen = guardarImagen(imagen);
            if (itemExistente.getImagen() != null) {
                eliminarImagen(itemExistente.getImagen());
            }
            itemExistente.setImagen("img/menu/" + nombreImagen);
        }

        return menuItemRepository.save(itemExistente);
    }

    @Override
    @Transactional
    public void eliminarItem(Long id) {
        MenuItem item = obtenerItemPorId(id);
        if (item.getImagen() != null) {
            eliminarImagen(item.getImagen());
        }
        menuItemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void cambiarDisponibilidad(Long id, boolean disponible) {
        MenuItem item = obtenerItemPorId(id);
        item.setDisponible(disponible);
        menuItemRepository.save(item);
    }

    @Override
    public List<String> obtenerCategoriasDisponibles() {
        return Arrays.asList("comida", "bebida", "postre");
    }

    private String guardarImagen(MultipartFile imagen) {
        try {
            if (imagen.isEmpty()) {
                throw new RuntimeException("No se puede guardar un archivo vacío");
            }

            String nombreOriginal = imagen.getOriginalFilename();
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
            String nombreArchivo = UUID.randomUUID().toString() + extension;

            Path destino = this.rootLocation.resolve(nombreArchivo);
            Files.copy(imagen.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            return nombreArchivo;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage(), e);
        }
    }

    private void eliminarImagen(String rutaImagen) {
        try {
            String nombreArchivo = Paths.get(rutaImagen).getFileName().toString();
            Path archivo = rootLocation.resolve(nombreArchivo);
            Files.deleteIfExists(archivo);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen: " + rutaImagen, e);
        }
    }
}
