package com.csprojectback.freelork.service.imp;

import com.csprojectback.freelork.dto.*;
import com.csprojectback.freelork.entity.*;
import com.csprojectback.freelork.exception.BusinessException;
import com.csprojectback.freelork.repository.*;
import com.csprojectback.freelork.service.CloudinaryService;
import com.csprojectback.freelork.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    StudentProjectRepository studentProjectRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void updateStudent(MultipartFile file, StudentDTO studentDTO) throws IOException {
        UserEntity userEntity = userRepository.findById(studentDTO.getId());
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);

        studentEntity.getUserEntity().setFullName(studentDTO.getFullName());
        studentEntity.setEnrollment(studentDTO.getEnrollment());
        studentEntity.getUserEntity().setEmail(studentDTO.getEmail());
        if (!studentDTO.getPassword().equals("null") && !passwordEncoder.matches(studentDTO.getPassword(), studentEntity.getUserEntity().getPassword()))
            studentEntity.getUserEntity().setPassword(passwordEncoder.encode(studentDTO.getPassword()));

        if (file != null) {
            if (studentEntity.getUserEntity().getImageId() != null)
                cloudinaryService.deleteFile(studentEntity.getUserEntity().getImageId());

            Map result = cloudinaryService.uploadFile(file, "/profile/student");
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
    public StudentDTO getStudent(int id) {
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
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);
        SummaryDTO summaryDTO = new SummaryDTO();
        List<RegisterDTO> registerDTOS = new ArrayList<>();
        List<ProjectRegistersDTO> projectRegistersDTOS = new ArrayList<>();

        int hours = 0;
        int i = 0;

        for (RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentEntity, 0)) {
            if (i < 6) {
                RegisterServiceImp.registerBaseDTO(registerDTOS, registerEntity);
                i++;
            }
            hours += registerEntity.getTimeRegister();
        }

        for (StudentProjectEntity studentProjectEntity : studentEntity.getStudentProjectEntities()) {
            ProjectRegistersDTO projectRegistersDTO = new ProjectRegistersDTO();

            projectRegistersDTO.setId(studentProjectEntity.getProjectEntity().getId());
            projectRegistersDTO.setNameProject(studentProjectEntity.getProjectEntity().getName());
            projectRegistersDTO.setRegisters(registerRepository.findByProjectEntityAndStatusNotAndStudentEntity(studentProjectEntity.getProjectEntity(), 0, studentEntity).size());
            projectRegistersDTOS.add(projectRegistersDTO);
        }

        summaryDTO.setRegisters(registerDTOS);
        summaryDTO.setProjects(projectRegistersDTOS);
        summaryDTO.setHours(hours);

        return summaryDTO;
    }

    @Override
    public List<ProjectDTO> getProjects(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);
        List<ProjectDTO> projectDTOS = new ArrayList<>();

        for (StudentProjectEntity studentProjectEntity : studentEntity.getStudentProjectEntities()) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(studentProjectEntity.getProjectEntity().getId());
            projectDTO.setName(studentProjectEntity.getProjectEntity().getName());
            projectDTO.setImageUrl(studentProjectEntity.getProjectEntity().getImageUrl());
            projectDTO.setDateCreated(studentProjectEntity.getProjectEntity().getDateCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            projectDTO.setObjectives(studentProjectEntity.getProjectEntity().getObjectives());
            projectDTO.setStatus(studentProjectEntity.getProjectEntity().getStatus());

            projectDTOS.add(projectDTO);
        }

        return projectDTOS;
    }

    @Override
    public void setProject(int idUser, int idProject) {
        UserEntity userEntity = userRepository.findById(idUser);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);
        ProjectEntity projectEntity = projectRepository.findById(idProject);

        StudentProjectEntity studentProjectEntity = new StudentProjectEntity();

        studentProjectEntity.setStudentEntity(studentEntity);
        studentProjectEntity.setProjectEntity(projectEntity);
        studentProjectEntity.setStatus(1);
        studentProjectEntity.setDateCreated(LocalDateTime.now());
        studentProjectEntity.setDateUpdated(LocalDateTime.now());

        if(studentProjectRepository.findByStudentEntityAndProjectEntity(studentEntity,projectEntity).isPresent())
            throw new BusinessException("Project already registered", HttpStatus.EXPECTATION_FAILED, "StudentController");
        studentProjectRepository.save(studentProjectEntity);
    }

    @Override
    public void deleteProject(int idUser, int idProject) {
        UserEntity userEntity = userRepository.findById(idUser);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);
        ProjectEntity projectEntity = projectRepository.findById(idProject);

        if(studentProjectRepository.findByStudentEntityAndProjectEntity(studentEntity,projectEntity).isEmpty())
            throw new BusinessException("Project not registered", HttpStatus.EXPECTATION_FAILED, "StudentController");
        studentProjectRepository.delete(studentProjectRepository.findByStudentEntityAndProjectEntity(studentEntity,projectEntity).get());
    }

    @Override
    public void setCompany(int idUser, int idCompany) {
        UserEntity userEntity = userRepository.findById(idUser);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);

        if(studentEntity.getCompanyEntity() != null)
            throw new BusinessException("Student already in a company", HttpStatus.EXPECTATION_FAILED, "StudentController");

        CompanyEntity companyEntity = companyRepository.findById(idCompany);
        studentEntity.setCompanyEntity(companyEntity);

        studentRepository.save(studentEntity);
    }

    @Override
    public List<ProjectRegistersDTO> getProjectsCompany(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);
        List<ProjectRegistersDTO> projectRegistersDTOS = new ArrayList<>();

        for(ProjectEntity projectEntity: projectRepository.findByCompanyEntity(studentEntity.getCompanyEntity())){
            ProjectRegistersDTO projectRegistersDTO = new ProjectRegistersDTO();
            projectRegistersDTO.setId(projectEntity.getId());
            projectRegistersDTO.setNameProject(projectEntity.getName());

            projectRegistersDTOS.add(projectRegistersDTO);
        }
        return projectRegistersDTOS;
    }

    @Override
    public ClassroomDTO getClassroom(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);
        ClassroomDTO classroomDTO = new ClassroomDTO();

       ClassroomEntity classroomEntity = studentEntity.getStudentClassroomEntity().getClassroomEntity();
       TeacherEntity teacherEntity = classroomEntity.getTeacherEntity();
       TeacherDTO teacherDTO = new TeacherDTO();

       classroomDTO.setName(classroomEntity.getName());
       classroomDTO.setCode(classroomEntity.getCode());
       classroomDTO.setGrade(classroomEntity.getClazzEntity().getGrade());
       classroomDTO.setSchedule(classroomEntity.getClazzEntity().getSchedule());

       teacherDTO.setName(teacherEntity.getUserEntity().getFullName());
       teacherDTO.setEmail(teacherEntity.getUserEntity().getEmail());
       teacherDTO.setPhone(teacherEntity.getPhone());
       teacherDTO.setGrade(teacherEntity.getGrade());
       teacherDTO.setImage(teacherEntity.getUserEntity().getImageUrl());

       classroomDTO.setTeacher(teacherDTO);

        return classroomDTO;
    }
}
