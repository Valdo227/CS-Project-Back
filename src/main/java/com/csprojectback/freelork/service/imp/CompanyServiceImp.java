package com.csprojectback.freelork.service.imp;

import com.csprojectback.freelork.dto.*;
import com.csprojectback.freelork.entity.*;
import com.csprojectback.freelork.exception.BusinessException;
import com.csprojectback.freelork.repository.*;
import com.csprojectback.freelork.service.CloudinaryService;
import com.csprojectback.freelork.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImp implements CompanyService {

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void updateCompany(MultipartFile file, CompanyDTO companyDTO) throws IOException {
        UserEntity userEntity = userRepository.findById(companyDTO.getId());
        CompanyEntity companyEntity = userEntity.getCompanyEntity();

        companyEntity.getUserEntity().setFullName(companyDTO.getFullName());
        companyEntity.getUserEntity().setEmail(companyDTO.getEmail());
        companyEntity.setServiceType(companyDTO.getServiceType());
        companyEntity.setSizeCompany(companyDTO.getSizeCompany());
        companyEntity.setHrEmail(companyDTO.getHrEmail());
        companyEntity.setHrFullName(companyDTO.getHrFullName());
        companyEntity.setHrPhone(companyDTO.getHrPhone());
        companyEntity.setAddress(companyDTO.getAddress());
        companyEntity.getUserEntity().setDateUpdated(LocalDateTime.now());

        if (!companyDTO.getPassword().equals("null") && !passwordEncoder.matches(companyDTO.getPassword(), companyEntity.getUserEntity().getPassword()))
            companyEntity.getUserEntity().setPassword(passwordEncoder.encode(companyDTO.getPassword()));

        if (file != null) {
            if (companyEntity.getUserEntity().getImageId() != null)
                cloudinaryService.deleteFile(companyEntity.getUserEntity().getImageId());
            Map cloudinary = cloudinaryService.uploadFile(file, "/profile/company");
            companyEntity.getUserEntity().setImageId(cloudinary.get("public_id").toString());
            companyEntity.getUserEntity().setImageUrl(cloudinary.get("secure_url").toString());

        }
        companyRepository.save(companyEntity);
    }

    @Override
    public SummaryCompanyDTO getSummary(int id) {

        UserEntity userEntity = userRepository.findById(id);
        CompanyEntity companyEntity = userEntity.getCompanyEntity();
        List<RegisterCompanyDTO> registerDTOS = new ArrayList<>();

        int acceptedTasks = 0, pendingTasks = 0, students = 0, hours = 0;
        for (StudentEntity studentEntity : studentRepository.findByCompanyEntity(companyEntity)) {
            for (RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentEntity, 0)) {
                if (registerEntity.getStatus() == 2) {
                    registerDTOS.add(setRegister(studentEntity, registerEntity));
                    pendingTasks++;
                } else if (registerEntity.getStatus() == 3) {
                    acceptedTasks++;
                    hours += registerEntity.getTimeRegister();
                }
            }
            students++;
        }
        return new SummaryCompanyDTO(
                students,
                acceptedTasks,
                pendingTasks,
                hours,
                registerDTOS
        );
    }

    @Override
    public List<ProjectDTO> getProjects(int id) {
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        UserEntity userEntity = userRepository.findById(id);
        CompanyEntity companyEntity = userEntity.getCompanyEntity();

        for (ProjectEntity projectEntity : projectRepository.findByCompanyEntityAndStatusNot(companyEntity, 0)) {
            projectDTOS.add(setProjectDTO(projectEntity));
        }
        return projectDTOS;
    }

    @Override
    public ProjectDTO getProject(int id) {
        ProjectEntity projectEntity = projectRepository.findById(id);
        return setProjectDTO(projectEntity);
    }

    @Override
    public void CreateProject(MultipartFile multipartFile, ProjectDTO projectDTO) throws IOException {
        ProjectEntity projectEntity = new ProjectEntity();
        UserEntity userEntity = userRepository.findById(projectDTO.getIdUser()).get();
        CompanyEntity companyEntity = userEntity.getCompanyEntity();

        if (!String.valueOf(projectDTO.getId()).equals("0")) {
            ProjectEntity projectSaved = projectRepository.getById(projectDTO.getId());

            projectEntity.setId(projectDTO.getId());
            projectEntity.setDateCreated(projectSaved.getDateCreated());

            if (multipartFile == null && !projectDTO.getImageId().equals("null") && projectDTO.getImageUrl().equals("null")) {
                cloudinaryService.deleteFile(projectDTO.getImageId());
                projectEntity.setImageUrl(null);
                projectEntity.setImageId(null);

            } else if (!projectDTO.getImageId().equals("null") && multipartFile != null) {
                cloudinaryService.deleteFile(projectDTO.getImageId());
                Map cloudinary = cloudinaryService.uploadFile(multipartFile, "/projects");
                projectEntity.setImageUrl(cloudinary.get("url").toString());
                projectEntity.setImageId(cloudinary.get("public_id").toString());
            } else if (multipartFile != null) {
                Map cloudinary = cloudinaryService.uploadFile(multipartFile, "/projects");
                projectEntity.setImageUrl(cloudinary.get("url").toString());
                projectEntity.setImageId(cloudinary.get("public_id").toString());
            } else {
                projectEntity.setImageId(projectSaved.getImageId());
                projectEntity.setImageUrl(projectSaved.getImageUrl());

            }
        } else {
            projectEntity.setDateUpdated(LocalDateTime.now());
            projectEntity.setDateCreated(LocalDateTime.now());

            if (multipartFile != null) {
                Map cloudinary = cloudinaryService.uploadFile(multipartFile, "/projects");
                projectEntity.setImageId(cloudinary.get("public_id").toString());
                projectEntity.setImageUrl(cloudinary.get("secure_url").toString());
            }
        }

        projectEntity.setCompanyEntity(companyEntity);
        projectEntity.setName(projectDTO.getName());
        projectEntity.setObjectives(projectDTO.getObjectives());
        projectEntity.setStatus(1);
        projectRepository.save(projectEntity);
    }

    @Override
    public void deleteProject(int id) {
        ProjectEntity projectEntity = projectRepository.findById(id);
        if (projectEntity.getStatus() == 0)
            throw new BusinessException("Project already deleted", HttpStatus.EXPECTATION_FAILED, "CompanyController");

        projectEntity.setStatus(0);
        projectRepository.save(projectEntity);
    }

    @Override
    public List<RegisterCompanyDTO> getRegisterList(int id) {
        List<RegisterCompanyDTO> registerDTOS = new ArrayList<>();
        UserEntity userEntity = userRepository.findById(id);
        CompanyEntity companyEntity = userEntity.getCompanyEntity();
        for (StudentEntity studentEntity : studentRepository.findByCompanyEntity(companyEntity)) {
            for (RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentEntity, 0)) {
                registerDTOS.add(setRegister(studentEntity, registerEntity));
            }
        }
        return registerDTOS;
    }

    public List<RegisterCompanyDTO> getRegisterListDate(int id, String date1, String date2) {
        List<RegisterCompanyDTO> registerDTOS = new ArrayList<>();
        UserEntity userEntity = userRepository.findById(id);
        CompanyEntity companyEntity = userEntity.getCompanyEntity();
        LocalDate startDate = LocalDate.parse(date1, format);
        LocalDate endDate = LocalDate.parse(date2, format);

        for (StudentEntity studentEntity : studentRepository.findByCompanyEntity(companyEntity)) {
            for (RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotAndDateRegisterBetweenOrderByIdDesc(studentEntity, 0, startDate, endDate)) {
                registerDTOS.add(setRegister(studentEntity, registerEntity));
            }
        }
        return registerDTOS;
    }

    @Override
    public void ChangeRegisterStatus(int id, int status) {
        RegisterEntity registerEntity = registerRepository.findById(id);
        registerEntity.setStatus(status);
        registerRepository.save(registerEntity);
    }

    @Override
    public List<CompanyStudentsDTO> getStudents(int id) {
        List<CompanyStudentsDTO> studentsDTOS = new ArrayList<>();
        UserEntity userEntity = userRepository.findById(id);
        CompanyEntity companyEntity = userEntity.getCompanyEntity();

        for (StudentEntity studentEntity : studentRepository.findByCompanyEntity(companyEntity)) {
            int hours = 0;
            for (RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentEntity, 0)) {
                hours += registerEntity.getTimeRegister();
            }
            UserEntity userStudent = studentEntity.getUserEntity();
            studentsDTOS.add(new CompanyStudentsDTO(
                    userStudent.getId(),
                    userStudent.getFullName(),
                    studentEntity.getEnrollment(),
                    userStudent.getEmail(),
                    userStudent.getImageUrl(),
                    studentEntity.getClassroomEntity() == null ? null : studentEntity.getClassroomEntity().getClazzEntity().getName(),
                    hours
            ));
        }
        return studentsDTOS;
    }

    @Override
    public CompanyProfileDTO getProfile(int id) {
        UserEntity userEntity = userRepository.findById(id);
        CompanyEntity companyEntity = userEntity.getCompanyEntity();

        return new CompanyProfileDTO(
                null,
                userEntity.getFullName(),
                userEntity.getEmail(),
                userEntity.getImageUrl(),
                companyEntity.getAddress(),
                companyEntity.getServiceType(),
                companyEntity.getSizeCompany(),
                companyEntity.getHrFullName(),
                companyEntity.getHrPhone(),
                companyEntity.getHrEmail()
        );
    }

    private ProjectDTO setProjectDTO(ProjectEntity projectEntity) {
        return new ProjectDTO(
                projectEntity.getId(),
                projectEntity.getImageUrl(),
                projectEntity.getImageId(),
                projectEntity.getName(),
                projectEntity.getDateCreated().format(format),
                projectEntity.getObjectives(),
                projectEntity.getStatus(),
                null
        );
    }

    private RegisterCompanyDTO setRegister(StudentEntity studentEntity, RegisterEntity registerEntity) {
        UserEntity userEntity = studentEntity.getUserEntity();
        return new RegisterCompanyDTO(
                registerEntity.getId(),
                registerEntity.getTitle(),
                registerEntity.getDateRegister().format(format),
                userEntity.getId(),
                userEntity.getFullName(),
                userEntity.getImageUrl(),
                registerEntity.getProjectEntity().getId(),
                registerEntity.getProjectEntity().getName(),
                registerEntity.getStatus(),
                registerEntity.getTimeRegister()
        );
    }
}