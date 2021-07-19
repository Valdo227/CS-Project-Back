package com.csprojectback.freelork.service;

import com.csprojectback.freelork.dto.RegisterDTO;
import com.csprojectback.freelork.entity.RegisterEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RegisterService {

    RegisterEntity createRegister(MultipartFile multipartFile,RegisterDTO registerDTO) throws IOException;

}
