package com.csprojectback.freelork.service;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {

    /*
    Metodo para crear la carpeta donde vamos a guardar los archivos
     */
    void init();

    /*
    Metodo para guardar los archivos
     */
    void save(MultipartFile file);

    /*
    Metodo para cargar un archivo
     */
    UrlResource load(String filename);

    /*
    Metodo para borrar todos los archivos cada vez que se inicie el servidor
     */
    void deleteAll();

    /*
    Metodo para Cargar todos los archivos
     */
    Stream<Path> loadAll();

    /*
    Metodo para Borrar un archivo
     */
    String deleteFile(String filename);

}
