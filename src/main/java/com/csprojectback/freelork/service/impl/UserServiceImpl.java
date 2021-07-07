package com.csprojectback.freelork.service.impl;

import com.csprojectback.freelork.entity.*;
import com.csprojectback.freelork.exception.BusinessException;
import com.csprojectback.freelork.repository.*;
import com.csprojectback.freelork.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

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
    CompanyRepository companyRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AdminEntity createAdmin(AdminEntity adminEntity) {

        createUser(adminEntity.getUserEntity());

        return adminRepository.save(adminEntity);
    }

    @Override
    public TeacherEntity createTeacher(TeacherEntity teacherEntity) {

        createUser(teacherEntity.getUserEntity());

        return teacherRepository.save(teacherEntity);
    }

    @Override
    public CompanyEntity createCompany(CompanyEntity companyEntity) {

        createUser(companyEntity.getUserEntity());

        return companyRepository.save(companyEntity);
    }

    @Override
    public StudentEntity createStudent(StudentEntity studentEntity) {

        createUser(studentEntity.getUserEntity());

        return studentRepository.save(studentEntity);
    }

    public void  createUser(UserEntity user){

        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());

        if (userEntity.isPresent())
            throw new BusinessException("Correo ya registrado", HttpStatus.FORBIDDEN, "UserController");

        user.setUserCode("user123");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1);
        user.setDateCreated(LocalDateTime.now());
        user.setDateUpdated(LocalDateTime.now());

        userRepository.save(user);

        String code = generateString(10- String.valueOf(user.getId()).length());

        user.setUserCode(user.getId()+code);

    }

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateString(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return builder.toString();
    }
}

