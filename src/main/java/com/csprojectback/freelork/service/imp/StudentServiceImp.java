package com.csprojectback.freelork.service.imp;

import com.csprojectback.freelork.dto.ProjectRegistersDTO;
import com.csprojectback.freelork.dto.RegisterDTO;
import com.csprojectback.freelork.dto.StudentDTO;
import com.csprojectback.freelork.dto.SummaryDTO;
import com.csprojectback.freelork.entity.*;
import com.csprojectback.freelork.repository.RegisterRepository;
import com.csprojectback.freelork.repository.StudentRepository;
import com.csprojectback.freelork.repository.UserRepository;
import com.csprojectback.freelork.service.CloudinaryService;
import com.csprojectback.freelork.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    RegisterRepository registerRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void updateStudent(MultipartFile file, StudentDTO studentDTO) throws IOException {
        UserEntity userEntity = userRepository.findById(studentDTO.getId());
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);

        studentEntity.getUserEntity().setFullName(studentDTO.getFullName());
        studentEntity.setEnrollment(studentDTO.getEnrollment());
        studentEntity.getUserEntity().setEmail(studentDTO.getEmail());
        if(!studentDTO.getPassword().equals("null") && !passwordEncoder.matches(studentDTO.getPassword(),studentEntity.getUserEntity().getPassword()))
            studentEntity.getUserEntity().setPassword(passwordEncoder.encode(studentDTO.getPassword()));

        if(file != null){
            if (studentEntity.getUserEntity().getImageId() != null)
                cloudinaryService.deleteFile(studentEntity.getUserEntity().getImageId());

            Map result = cloudinaryService.uploadFile(file,"/profile/student");
            studentEntity.getUserEntity().setImageId(result.get("public_id").toString());
            studentEntity.getUserEntity().setImageUrl(result.get("secure_url").toString());

        }
        /*
        else
            if (studentEntity.getUserEntity().getImageUrl() != null)
                cloudinaryService.deleteFile(studentEntity.getUserEntity().getImageId());

         */
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

    @Override
    public SummaryDTO getSummary(int id) {
        SummaryDTO summaryDTO = new SummaryDTO();
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);
        List<RegisterDTO> registerDTOS = new ArrayList<>();
        List<ProjectRegistersDTO> projectRegistersDTOS = new ArrayList<>();
        int hours = 0;
        int i=0;

        for (RegisterEntity registerEntity: registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentEntity, 0)){
            if(i<6){
                RegisterServiceImp.registerBaseDTO(registerDTOS, registerEntity);
                i++;
            }
            hours += registerEntity.getTimeRegister();
        }

        for(StudentProjectEntity studentProjectEntity: studentEntity.getStudentProjectEntities()){
            ProjectRegistersDTO projectRegistersDTO = new ProjectRegistersDTO();

            projectRegistersDTO.setNameProject(studentProjectEntity.getProjectEntity().getName());
            projectRegistersDTO.setRegisters(registerRepository.findByProjectEntityAndStatusNotAndStudentEntity(studentProjectEntity.getProjectEntity(), 0, studentEntity).size());
            projectRegistersDTOS.add(projectRegistersDTO);
        }

        summaryDTO.setRegisters(registerDTOS);
        summaryDTO.setProjects(projectRegistersDTOS);
        summaryDTO.setHours(hours);

        return summaryDTO;
    }

}
