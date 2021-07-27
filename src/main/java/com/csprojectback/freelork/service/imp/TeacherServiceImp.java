package com.csprojectback.freelork.service.imp;

import com.csprojectback.freelork.dto.ClassroomTeacherDTO;
import com.csprojectback.freelork.dto.RegisterDTO;
import com.csprojectback.freelork.dto.StudentSummaryDTO;
import com.csprojectback.freelork.dto.SummaryTeacherDTO;
import com.csprojectback.freelork.entity.*;
import com.csprojectback.freelork.repository.*;
import com.csprojectback.freelork.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public SummaryTeacherDTO getSummary(int id) {
        SummaryTeacherDTO summaryTeacherDTO = new SummaryTeacherDTO();

        UserEntity userEntity = userRepository.findById(id);
        TeacherEntity teacherEntity = userEntity.getTeacherEntity();
        List<RegisterDTO> registerDTOS = new ArrayList<>(),registerSorted = new ArrayList<>();

        int i = 0, tasks = 0, students = 0, groups = 0;


        for(ClassroomEntity classroomEntity : classroomRepository.findByTeacherEntityAndStatusNot(teacherEntity, 0)){
            for(StudentClassroomEntity studentClassroomEntity: classroomEntity.getStudentClassroomEntity()){
                for(RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentClassroomEntity.getStudentEntity(),0)){
                    RegisterServiceImp.registerBaseDTO(registerDTOS, registerEntity);
                    tasks++;
                }
                students++;
            }
            groups++;
        }

        registerDTOS = registerDTOS.stream()
                .sorted(Comparator.comparingInt(RegisterDTO::getId).reversed())
                .collect(Collectors.toList());

        for (RegisterDTO registerDTO: registerDTOS){
            if (i < 6) {
                registerSorted.add(registerDTO);
                i++;
            }
        }
        summaryTeacherDTO.setRegisters(registerSorted);
        summaryTeacherDTO.setTasks(tasks);
        summaryTeacherDTO.setStudents(students);
        summaryTeacherDTO.setGroups(groups);

        return summaryTeacherDTO;
    }

    @Override
    public List<ClassroomTeacherDTO> getClassrooms(int id) {
        List<ClassroomTeacherDTO> classroomDTOS = new ArrayList<>();

        UserEntity userEntity = userRepository.findById(id);
        TeacherEntity teacherEntity = userEntity.getTeacherEntity();

        for(ClassroomEntity classroomEntity : classroomRepository.findByTeacherEntityAndStatusNot(teacherEntity, 0)){
            ClassroomTeacherDTO classroomDTO  = new ClassroomTeacherDTO();
            classroomDTO.setId(classroomEntity.getId());
            classroomDTO.setName(classroomEntity.getName());
            classroomDTO.setCode(classroomEntity.getCode());
            classroomDTO.setDateCreated(classroomEntity.getDateCreated().format(format));
            classroomDTO.setCareer(classroomEntity.getClazzEntity().getCareerName());
            classroomDTO.setStudents(classroomEntity.getStudentClassroomEntity().size());
            classroomDTO.setStatus(classroomEntity.getStatus());

            classroomDTOS.add(classroomDTO);
        }
            return classroomDTOS;
    }

    @Override
    public List<StudentSummaryDTO> getStudents(int id) {
        List<StudentSummaryDTO> studentDTOS = new ArrayList<>();

        UserEntity userEntity = userRepository.findById(id);
        TeacherEntity teacherEntity = userEntity.getTeacherEntity();

        for(ClassroomEntity classroomEntity : classroomRepository.findByTeacherEntityAndStatusNot(teacherEntity, 0)){
            for(StudentClassroomEntity studentClassroomEntity: classroomEntity.getStudentClassroomEntity()){
                int hours = 0;
                StudentEntity studentEntity = studentClassroomEntity.getStudentEntity();
                StudentSummaryDTO studentDTO = new StudentSummaryDTO();

                for (RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentEntity, 0))
                    hours += registerEntity.getTimeRegister();

                studentDTO.setId(studentEntity.getId());
                studentDTO.setEnrollment(studentEntity.getEnrollment());
                studentDTO.setFullName(studentEntity.getUserEntity().getFullName());
                studentDTO.setClassroom(classroomEntity.getName());
                studentDTO.setImage(studentEntity.getUserEntity().getImageUrl());
                studentDTO.setCompany(studentEntity.getCompanyEntity().getUserEntity().getFullName());
                studentDTO.setHours(hours);

                studentDTOS.add(studentDTO);
            }
        }

        return studentDTOS;
    }
}
