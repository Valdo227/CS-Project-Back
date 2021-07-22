package com.csprojectback.freelork.service.imp;

import com.csprojectback.freelork.dto.StudentDTO;
import com.csprojectback.freelork.entity.StudentEntity;
import com.csprojectback.freelork.entity.UserEntity;
import com.csprojectback.freelork.repository.StudentRepository;
import com.csprojectback.freelork.repository.UserRepository;
import com.csprojectback.freelork.service.CloudinaryService;
import com.csprojectback.freelork.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class StudentServiceImp implements StudentService {

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void updateStudent(MultipartFile file, StudentDTO studentDTO) throws IOException {
        UserEntity userEntity = userRepository.findById(studentDTO.getId());
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);

        studentEntity.getUserEntity().setFullName(studentDTO.getFullName());
        studentEntity.setEnrollment(studentDTO.getEnrollment());
        studentEntity.getUserEntity().setEmail(studentDTO.getEmail());
        if(studentDTO.getPassword()!=null && !passwordEncoder.matches(studentDTO.getPassword(),studentEntity.getUserEntity().getPassword()))
            studentEntity.getUserEntity().setPassword(passwordEncoder.encode(studentDTO.getPassword()));

        if(file != null){
            if (studentEntity.getUserEntity().getImageId() != null)
                cloudinaryService.deleteFile(studentEntity.getUserEntity().getImageId());

            Map result = cloudinaryService.uploadFile(file,"/profile/student");
            studentEntity.getUserEntity().setImageId(result.get("public_id").toString());
            studentEntity.getUserEntity().setImageUrl(result.get("url").toString());

        }else
            if (studentEntity.getUserEntity().getImageId() != null)
                cloudinaryService.deleteFile(studentEntity.getUserEntity().getImageId());

        studentRepository.save(studentEntity);

    }

    @Override
    public StudentDTO getStudent(int id){
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setFullName(studentEntity.getUserEntity().getFullName());
        studentDTO.setEmail(studentEntity.getUserEntity().getEmail());
        studentDTO.setEnrollment(studentEntity.getEnrollment());
        studentDTO.setImageUrl(studentEntity.getUserEntity().getImageUrl());

        return studentDTO;
    }
}
