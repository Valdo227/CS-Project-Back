package com.csprojectback.freelork.service;

import com.csprojectback.freelork.dto.StudentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentService {


    void updateStudent(MultipartFile file, StudentDTO studentDTO) throws IOException;

    StudentDTO getStudent(int id);


}
