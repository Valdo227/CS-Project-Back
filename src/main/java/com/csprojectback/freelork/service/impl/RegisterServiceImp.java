package com.csprojectback.freelork.service.impl;

import com.csprojectback.freelork.entity.RegisterEntity;
import com.csprojectback.freelork.repository.RegisterRepository;
import com.csprojectback.freelork.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegisterServiceImp implements RegisterService {

    @Autowired
    RegisterRepository registerRepository;

    @Override
    public RegisterEntity createRegister(RegisterEntity registerEntity){


        registerEntity.setStatus(1);
        registerEntity.setDateCreated(LocalDateTime.now());
        registerEntity.setDateUpdated(LocalDateTime.now());


        return registerRepository.save(registerEntity);
    }
}
