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
import java.util.Optional;

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
    ClassroomRepository classroomRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void updateStudent(MultipartFile file, StudentDTO studentDTO) throws IOException {
        UserEntity userEntity = userRepository.findById(studentDTO.getId());
        StudentEntity studentEntity = userEntity.getStudentEntity();

        studentEntity.getUserEntity().setFullName(studentDTO.getFullName());
        studentEntity.setEnrollment(studentDTO.getEnrollment());
        studentEntity.getUserEntity().setEmail(studentDTO.getEmail());
        studentEntity.getUserEntity().setDateUpdated(LocalDateTime.now());

        if (!studentDTO.getPassword().equals("null") && !passwordEncoder.matches(studentDTO.getPassword(), studentEntity.getUserEntity().getPassword()))
            studentEntity.getUserEntity().setPassword(passwordEncoder.encode(studentDTO.getPassword()));

        if (file != null) {
            if (studentEntity.getUserEntity().getImageId() != null)
                cloudinaryService.deleteFile(studentEntity.getUserEntity().getImageId());

            Map result = cloudinaryService.uploadFile(file, "/profile/student");
            studentEntity.getUserEntity().setImageId(result.get("public_id").toString());
            studentEntity.getUserEntity().setImageUrl(result.get("secure_url").toString());

        }
        studentRepository.save(studentEntity);

    }

    @Override
    public StudentDTO getStudent(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setFullName(userEntity.getFullName());
        studentDTO.setEmail(userEntity.getEmail());
        studentDTO.setEnrollment(studentEntity.getEnrollment());
        studentDTO.setImageUrl(userEntity.getImageUrl());

        return studentDTO;
    }

    @Override
    public StudentProfileDTO getProfile(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        StudentProfileDTO studentProfileDTO = new StudentProfileDTO();

        studentProfileDTO.setFullName(userEntity.getFullName());
        studentProfileDTO.setEmail(userEntity.getEmail());
        studentProfileDTO.setRole(userEntity.getRole());
        studentProfileDTO.setEnrollment(studentEntity.getEnrollment());
        studentProfileDTO.setCompany(studentEntity.getCompanyEntity().getUserEntity().getFullName());
        studentProfileDTO.setCareer(studentEntity.getClassroomEntity().getClazzEntity().getCareerName());

        return studentProfileDTO;
    }

    @Override
    public SummaryStudentDTO getSummary(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        SummaryStudentDTO summaryDTO = new SummaryStudentDTO();
        List<RegisterDTO> registerDTOS = new ArrayList<>();
        int hours = 0;
        int i = 0;

        for (RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentEntity, 0)) {
            if (i < 6) {
                registerDTOS.add(RegisterServiceImp.registerBaseDTO(registerEntity));
                i++;
            }
            hours += registerEntity.getTimeRegister();
        }
        summaryDTO.setRegisters(registerDTOS);
        summaryDTO.setProjects(setProjects(studentEntity, registerRepository));
        summaryDTO.setHours(hours);

        return summaryDTO;
    }

    @Override
    public List<ProjectDTO> getProjects(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        List<ProjectDTO> projectDTOS = new ArrayList<>();

        for (StudentProjectEntity studentProjectEntity : studentEntity.getStudentProjectEntities()) {
            if(studentProjectEntity.getStatus() != 0) {
                ProjectEntity projectEntity = studentProjectEntity.getProjectEntity();
                projectDTOS.add(new ProjectDTO(
                        projectEntity.getId(),
                        projectEntity.getImageUrl(),
                        null,
                        projectEntity.getName(),
                        projectEntity.getDateCreated().format(format),
                        projectEntity.getObjectives(),
                        projectEntity.getStatus(),
                        null

                ));
            }
        }
        return projectDTOS;
    }

    @Override
    public void setProject(int idUser, int idProject) {
        UserEntity userEntity = userRepository.findById(idUser);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        ProjectEntity projectEntity = projectRepository.findById(idProject);

        if(studentProjectRepository.findByStudentEntityAndProjectEntityAndStatusNot(studentEntity,projectEntity, 0).isPresent())
            throw new BusinessException("Project already registered", HttpStatus.EXPECTATION_FAILED, "StudentController");

        StudentProjectEntity studentProjectEntity = new StudentProjectEntity();

        studentProjectEntity.setStudentEntity(studentEntity);
        studentProjectEntity.setProjectEntity(projectEntity);
        studentProjectEntity.setStatus(1);
        studentProjectEntity.setDateCreated(LocalDateTime.now());
        studentProjectEntity.setDateUpdated(LocalDateTime.now());

        studentProjectRepository.save(studentProjectEntity);
    }

    @Override
    public void deleteProject(int idUser, int idProject) {
        UserEntity userEntity = userRepository.findById(idUser);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        ProjectEntity projectEntity = projectRepository.findById(idProject);
        Optional<StudentProjectEntity> studentProjectEntity = studentProjectRepository.findByStudentEntityAndProjectEntityAndStatusNot(studentEntity,projectEntity, 0);

        if(studentProjectEntity.isEmpty())
            throw new BusinessException("Project not registered", HttpStatus.EXPECTATION_FAILED, "StudentController");
        studentProjectEntity.get().setStatus(0);
        studentProjectRepository.save(studentProjectEntity.get());
    }

    @Override
    public List<CompanyProfileDTO> getCompanies() {
        List<CompanyProfileDTO> companyDTOS = new ArrayList<>();
        for (CompanyEntity companyEntity: companyRepository.findAll()){
            UserEntity userEntity = companyEntity.getUserEntity();
            companyDTOS.add(new CompanyProfileDTO(
                    userEntity.getId(),
                    userEntity.getFullName(),
                    userEntity.getEmail(),
                    userEntity.getImageUrl(),
                    companyEntity.getAddress(),
                    companyEntity.getServiceType(),
                    companyEntity.getSizeCompany(),
                    companyEntity.getHrFullName(),
                    companyEntity.getHrPhone(),
                    companyEntity.getHrEmail()
            ));
        }
        return companyDTOS;
    }

    @Override
    public void setCompany(int idUser, int idCompany) {
        UserEntity userEntity = userRepository.findById(idUser);
        StudentEntity studentEntity = userEntity.getStudentEntity();

        if(studentEntity.getCompanyEntity() != null)
            throw new BusinessException("Student already in a company", HttpStatus.EXPECTATION_FAILED, "StudentController");

        CompanyEntity companyEntity = companyRepository.findById(idCompany);
        studentEntity.setCompanyEntity(companyEntity);

        studentRepository.save(studentEntity);
    }

    @Override
    public void deleteCompany(int idUser, int idCompany) {
        UserEntity userEntity = userRepository.findById(idUser);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        if(studentEntity.getCompanyEntity() == null)
            throw new BusinessException("Student not registered", HttpStatus.EXPECTATION_FAILED, "StudentController");

        for(StudentProjectEntity studentProjectEntity : studentProjectRepository.findByStudentEntityAndStatusNot(studentEntity,0)){
            if(studentProjectEntity.getProjectEntity().getCompanyEntity() == studentEntity.getCompanyEntity()){
                studentProjectEntity.setStatus(0);
                studentProjectRepository.save(studentProjectEntity);
            }
        }
        studentEntity.setCompanyEntity(null);
        studentRepository.save(studentEntity);
    }

    @Override
    public List<ProjectRegistersDTO> getProjectsCompany(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        List<ProjectRegistersDTO> projectRegistersDTOS = new ArrayList<>(),projectRegisterClean = new ArrayList<>();

        for(ProjectEntity projectEntity: projectRepository.findByCompanyEntityAndStatusNot(studentEntity.getCompanyEntity(), 0)){
                ProjectRegistersDTO projectRegistersDTO = new ProjectRegistersDTO(
                        projectEntity.getId(),
                        projectEntity.getName(),
                        null
                );
                projectRegistersDTOS.add(projectRegistersDTO);
                projectRegisterClean.add(projectRegistersDTO);
        }

        for(ProjectRegistersDTO projectRegistersDTO : projectRegistersDTOS ) {
            for (StudentProjectEntity studentProjectEntity : studentProjectRepository.findByStudentEntityAndStatusNot(studentEntity,0)) {
                if (studentProjectEntity.getProjectEntity().getId() == projectRegistersDTO.getId()) {
                    projectRegisterClean.remove(projectRegistersDTO);
                }
            }
        }

        return projectRegisterClean;
    }

    @Override
    public ClassroomDTO getClassroom(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = userEntity.getStudentEntity();

       ClassroomEntity classroomEntity = studentEntity.getClassroomEntity();
       TeacherEntity teacherEntity = classroomEntity.getTeacherEntity();
       UserEntity userTeacher = teacherEntity.getUserEntity();

       TeacherDTO teacherDTO = new TeacherDTO(
               null,
               userTeacher.getFullName(),
               teacherEntity.getGrade(),
               userTeacher.getEmail(),
               teacherEntity.getPhone(),
               userEntity.getImageUrl(),
               null
       );
        return new ClassroomDTO(
                classroomEntity.getName(),
                classroomEntity.getCode(),
                classroomEntity.getClazzEntity().getSchedule(),
                classroomEntity.getClazzEntity().getGrade(),
                teacherDTO
        );
    }

    @Override
    public void setClassroom(int id, String code) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        Optional<ClassroomEntity> classroomEntity = classroomRepository.findByCodeAndStatusNot(code,0);
        if(classroomEntity.isEmpty())
            throw new BusinessException("Classroom not exist", HttpStatus.EXPECTATION_FAILED, "StudentController");

        if(studentEntity.getClassroomEntity() == classroomEntity.get())
            throw new BusinessException("Student already in the classroom", HttpStatus.EXPECTATION_FAILED, "StudentController");

        studentEntity.setClassroomEntity(classroomEntity.get());

        studentRepository.save(studentEntity);
    }

    @Override
    public void deleteClassroom(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        if(studentEntity.getClassroomEntity() == null)
            throw new BusinessException("Student not in the classroom", HttpStatus.EXPECTATION_FAILED, "StudentController");

        studentEntity.setClassroomEntity(null);

        studentRepository.save(studentEntity);
    }

    @Override
    public CompanyDTO getCompany(int id) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        CompanyEntity companyEntity = studentEntity.getCompanyEntity();
        return new CompanyDTO(
                companyEntity.getId(),
                userEntity.getFullName(),
                userEntity.getEmail(),
                companyEntity.getServiceType(),
                companyEntity.getSizeCompany(),
                companyEntity.getAddress(),
                companyEntity.getHrFullName(),
                companyEntity.getHrPhone(),
                companyEntity.getHrEmail(),
                userEntity.getImageUrl(),
                null
        );
    }

    static List<ProjectRegistersDTO> setProjects(StudentEntity studentEntity, RegisterRepository registerRepository) {
        List<ProjectRegistersDTO> projectRegistersDTOS = new ArrayList<>();
        for (StudentProjectEntity studentProjectEntity : studentEntity.getStudentProjectEntities()) {
            if(studentProjectEntity.getStatus() !=0) {
                ProjectRegistersDTO projectRegistersDTO = new ProjectRegistersDTO(
                        studentProjectEntity.getProjectEntity().getId(),
                        studentProjectEntity.getProjectEntity().getName(),
                        registerRepository.findByProjectEntityAndStatusNotAndStudentEntity(studentProjectEntity.getProjectEntity(), 0, studentEntity).size()
                );
                projectRegistersDTOS.add(projectRegistersDTO);
            }
        }
        return projectRegistersDTOS;
    }
}