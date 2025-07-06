package com.RestauranteWeb.restauranteweb.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads/img/menu";


    public String guardar(MultipartFile archivo) throws IOException {
        String originalFilename = archivo.getOriginalFilename();
        if (originalFilename == null) {
            originalFilename = "archivo";
        }
        String nombreArchivo = System.currentTimeMillis() + "_" + StringUtils.cleanPath(originalFilename);

        File directorio = new File(UPLOAD_DIR);
        if (!directorio.exists()) directorio.mkdirs();

        File destino = new File(directorio, nombreArchivo);
        archivo.transferTo(destino);

        return nombreArchivo;
    }
}
