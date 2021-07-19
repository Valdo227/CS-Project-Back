package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.dto.RegisterDTO;
import com.csprojectback.freelork.entity.RegisterEntity;
import com.csprojectback.freelork.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHENTICATION_BASE;

@RestController
@RequestMapping(URL_PRIVATE_AUTHENTICATION_BASE +"register/")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @PostMapping("")
    public ResponseEntity<String> createRegister(@RequestParam(name = "file" ,required = false)MultipartFile file, RegisterDTO registerDTO){
        try {
            registerService.createRegister(file,registerDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Ok");
        } catch (IOException e) {
            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }

    }

}
