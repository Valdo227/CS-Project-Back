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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public void createRegister(MultipartFile multipartFile, RegisterDTO registerDTO) throws IOException {


        RegisterEntity registerEntity = new RegisterEntity();

        StudentEntity studentEntity = studentRepository.findById(registerDTO.getIdUser());

        ProjectEntity projectEntity = projectRepository.findById(registerDTO.getIdProject());

        if(!String.valueOf(registerDTO.getId()).equals("0")){
            RegisterEntity registerSaved = registerRepository.getById(registerDTO.getId());

            registerEntity.setId(registerDTO.getId());
            registerEntity.setDateCreated(registerSaved.getDateCreated());

            if(multipartFile == null){
                Map result = cloudinaryService.deleteFile(registerDTO.getImageId());
                registerEntity.setImageId(null);
                registerEntity.setImageUrl(null);

            }
            else {
                cloudinaryService.deleteFile(registerDTO.getImageId());
                Map result = cloudinaryService.uploadFile(multipartFile, "/register");
                registerEntity.setImageId(result.get("public_id").toString());
                registerEntity.setImageUrl(result.get("url").toString());
            }
        }
        else {

            registerEntity.setDateCreated(LocalDateTime.now());

            if (multipartFile != null) {
                Map result = cloudinaryService.uploadFile(multipartFile, "/register");
                registerEntity.setImageId(result.get("public_id").toString());
                registerEntity.setImageUrl(result.get("url").toString());
            }
        }

        registerEntity.setDateRegister(LocalDate.parse(registerDTO.getDateRegister(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        registerEntity.setTimeRegister(registerDTO.getTimeRegister());
        registerEntity.setTitle(registerDTO.getTitle());
        registerEntity.setDescription((registerDTO.getDescription().equals("")) ? null: registerDTO.getDescription());

        registerEntity.setStatus(1);
        registerEntity.setDateUpdated(LocalDateTime.now());
        registerEntity.setStudentEntity(studentEntity);
        registerEntity.setProjectEntity(projectEntity);

        registerRepository.save(registerEntity);
    }
}
