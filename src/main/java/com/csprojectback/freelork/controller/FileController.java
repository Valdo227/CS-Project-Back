package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.entity.FileEntity;
import com.csprojectback.freelork.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHENTICATION_BASE;

@RestController
@RequestMapping(URL_PRIVATE_AUTHENTICATION_BASE +"file/")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })
public class FileController {
    //Inyectamos el servicio
    @Autowired
    FileService fileService;

    @PostMapping("upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files){
        String message;
        try{
            List<String> fileNames = new ArrayList<>();

            Arrays.stream(files).forEach(file->{
                fileService.save(file);
                fileNames.add(file.getOriginalFilename());
            });

            message = "Se subieron los archivos correctamente " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }catch (Exception e){
            message = "Fallo al subir los archivos";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<FileEntity>> getFiles(){
        List<FileEntity> fileInfos = fileService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile",
                    path.getFileName().toString()).build().toString();
            return new FileEntity(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }


    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename){
        Resource file = fileService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""+file.getFilename() + "\"").body(file);
    }

    @GetMapping("delete/{filename:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        String message = "";
        try {
            message = fileService.deleteFile(filename);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

}
