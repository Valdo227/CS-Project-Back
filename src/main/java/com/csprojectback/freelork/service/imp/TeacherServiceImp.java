package com.csprojectback.freelork.service.imp;

import com.csprojectback.freelork.dto.RegisterDTO;
import com.csprojectback.freelork.dto.SummaryTeacherDTO;
import com.csprojectback.freelork.entity.*;
import com.csprojectback.freelork.repository.*;
import com.csprojectback.freelork.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImp implements TeacherService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentClassroomRepository studentClassroomRepository;


    public SummaryTeacherDTO getSummary(int id) {
        SummaryTeacherDTO summaryTeacherDTO = new SummaryTeacherDTO();

        UserEntity userEntity = userRepository.findById(id);
        TeacherEntity teacherEntity = teacherRepository.findByUserEntity(userEntity);

        List<ClassroomEntity> classroomEntityList = classroomRepository.findByTeacherEntityAndStatusNot(teacherEntity, 0);

        List<RegisterDTO> registerDTOS = new ArrayList<>();

        
        int tasks = 0;

        int i=0;

        summaryTeacherDTO.setRegisters(registerDTOS);
        summaryTeacherDTO.setTotalTasks(tasks);

        return summaryTeacherDTO;
    }
}
