package com.csprojectback.freelork.service.impl;

import com.csprojectback.freelork.entity.AdminEntity;
import com.csprojectback.freelork.entity.TeacherEntity;
import com.csprojectback.freelork.entity.UserEntity;
import com.csprojectback.freelork.exception.BusinessException;
import com.csprojectback.freelork.repository.AdminRepository;
import com.csprojectback.freelork.repository.TeacherRepository;
import com.csprojectback.freelork.repository.UserRepository;
import com.csprojectback.freelork.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
     public AdminEntity createAdmin(AdminEntity adminEntity){
        Optional<UserEntity> userEntity = userRepository.findByEmail(adminEntity.getUserEntity().getEmail());

        if(userEntity.isPresent())
            throw new BusinessException("Correo ya registrado", HttpStatus.FORBIDDEN, "UserController");

        adminEntity.getUserEntity().setPassword(passwordEncoder.encode(adminEntity.getUserEntity().getPassword()));
        adminEntity.getUserEntity().setStatus(1);
        adminEntity.getUserEntity().setDateCreated(LocalDateTime.now());
        adminEntity.getUserEntity().setDateUpdated(LocalDateTime.now());

        return adminRepository.save(adminEntity);
    }

    @Override
    public TeacherEntity createTeacher(TeacherEntity teacherEntity){
        Optional<UserEntity> userEntity = userRepository.findByEmail(teacherEntity.getUserEntity().getEmail());

        if(userEntity.isPresent())
            throw new BusinessException("Correo ya registrado", HttpStatus.FORBIDDEN, "UserController");

        teacherEntity.getUserEntity().setPassword(passwordEncoder.encode(teacherEntity.getUserEntity().getPassword()));
        teacherEntity.getUserEntity().setStatus(1);
        teacherEntity.getUserEntity().setDateCreated(LocalDateTime.now());
        teacherEntity.getUserEntity().setDateUpdated(LocalDateTime.now());

        return teacherRepository.save(teacherEntity);
    }

}
