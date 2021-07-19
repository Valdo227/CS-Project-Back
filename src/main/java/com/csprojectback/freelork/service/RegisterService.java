package com.csprojectback.freelork.service;

import com.csprojectback.freelork.dto.RegisterDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RegisterService {

    void createRegister(MultipartFile multipartFile, RegisterDTO registerDTO) throws IOException;

}
