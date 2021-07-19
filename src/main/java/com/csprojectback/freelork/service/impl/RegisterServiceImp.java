package com.csprojectback.freelork.service.impl;

import com.csprojectback.freelork.dto.RegisterDTO;
import com.csprojectback.freelork.entity.ProjectEntity;
import com.csprojectback.freelork.entity.RegisterEntity;
import com.csprojectback.freelork.entity.StudentEntity;
import com.csprojectback.freelork.repository.ProjectRepository;
import com.csprojectback.freelork.repository.RegisterRepository;
import com.csprojectback.freelork.repository.StudentRepository;
import com.csprojectback.freelork.service.CloudinaryService;
import com.csprojectback.freelork.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class RegisterServiceImp implements RegisterService {

    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public RegisterEntity createRegister(MultipartFile multipartFile, RegisterDTO registerDTO) throws IOException {
        RegisterEntity registerEntity = new RegisterEntity();

        StudentEntity studentEntity = studentRepository.findById(registerDTO.getIdUser());

        ProjectEntity projectEntity = projectRepository.findById(registerDTO.getIdProject());

        Map result = cloudinaryService.uploadFile(multipartFile,"/register");

        registerEntity.setDateRegister(registerDTO.getDateRegister());
        registerEntity.setTimeRegister(registerDTO.getTimeRegister());
        registerEntity.setTitle(registerDTO.getTitle());
        registerEntity.setDescription(registerDTO.getDescription());
        registerEntity.setImageId(result.get("public_id").toString());
        registerEntity.setImageUrl(result.get("url").toString());
        registerEntity.setStatus(1);
        registerEntity.setDateCreated(LocalDateTime.now());
        registerEntity.setDateUpdated(LocalDateTime.now());
        registerEntity.setStudentEntity(studentEntity);
        registerEntity.setProjectEntity(projectEntity);

        return registerRepository.save(registerEntity);
    }
}
