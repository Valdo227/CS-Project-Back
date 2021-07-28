package com.csprojectback.freelork.service.imp;

import com.csprojectback.freelork.dto.*;
import com.csprojectback.freelork.entity.*;
import com.csprojectback.freelork.repository.*;
import com.csprojectback.freelork.service.CloudinaryService;
import com.csprojectback.freelork.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CompanyServiceImp implements CompanyService{

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

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void updateCompany(MultipartFile file, CompanyDTO companyDTO) throws IOException {
        UserEntity userEntity = userRepository.findById(companyDTO.getId());
        CompanyEntity companyEntity = userEntity.getCompanyEntity();

        companyEntity.getUserEntity().setFullName(companyDTO.getName());
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

            Map result = cloudinaryService.uploadFile(file, "/profile/company");
            companyEntity.getUserEntity().setImageId(result.get("public_id").toString());
            companyEntity.getUserEntity().setImageUrl(result.get("secure_url").toString());

        }
        companyRepository.save(companyEntity);
    }

    @Override
    public SummaryCompanyDTO getSummary(int id) {
        SummaryCompanyDTO summaryCompanyDTO = new SummaryCompanyDTO();
        UserEntity userEntity = userRepository.findById(id);
        CompanyEntity companyEntity = userEntity.getCompanyEntity();
        List<RegisterCompanyDTO> registerCompanyDTOS = new ArrayList<>();

        int acceptedTasks = 0, pendingTasks = 0, students = 0,hours = 0;
        for(StudentEntity studentEntity: studentRepository.findByCompanyEntity(companyEntity)){
            for(RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentEntity,0)){
                if(registerEntity.getStatus() == 2) {
                    RegisterCompanyDTO registerDTO = new RegisterCompanyDTO();
                    registerDTO.setId(registerEntity.getId());
                    registerDTO.setTitle(registerEntity.getTitle());
                    registerDTO.setDateCreated(registerEntity.getDateRegister().format(format));
                    registerDTO.setHours(registerEntity.getTimeRegister());
                    registerDTO.setNameStudent(studentEntity.getUserEntity().getFullName());
                    registerDTO.setImageStudent(studentEntity.getUserEntity().getImageUrl());
                    registerDTO.setIdProject(registerEntity.getProjectEntity().getId());
                    registerDTO.setNameProject(registerEntity.getProjectEntity().getName());
                    registerDTO.setStatus(registerEntity.getStatus());
                    registerCompanyDTOS.add(registerDTO);
                    pendingTasks++;
                }
                else if(registerEntity.getStatus() == 3) {
                    acceptedTasks++;
                    hours += registerEntity.getTimeRegister();
                }
            }
            students++;
        }
        summaryCompanyDTO.setStudents(students);
        summaryCompanyDTO.setAcceptedTasks(acceptedTasks);
        summaryCompanyDTO.setPendingTasks(pendingTasks);
        summaryCompanyDTO.setHours(hours);
        summaryCompanyDTO.setRegisters(registerCompanyDTOS);

        return summaryCompanyDTO;
    }

    @Override
    public List<ProjectDTO> getProjects(int id) {
        List<ProjectDTO> projectDTOS =  new ArrayList<>();
        UserEntity userEntity = userRepository.findById(id);
        CompanyEntity companyEntity = userEntity.getCompanyEntity();

        for(ProjectEntity projectEntity: projectRepository.findByCompanyEntityAndStatusNot(companyEntity, 0)){
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(projectEntity.getId());
            projectDTO.setName(projectEntity.getName());
            projectDTO.setImageUrl(projectEntity.getImageUrl());
            projectDTO.setDateCreated(projectEntity.getDateCreated().format(format));
            projectDTO.setObjectives(projectEntity.getObjectives());
            projectDTO.setStatus(projectEntity.getStatus());

            projectDTOS.add(projectDTO);
        }
        return projectDTOS;
    }

    @Override
    public void CreateProject(MultipartFile multipartFile, ProjectDTO projectDTO) throws IOException {
        ProjectEntity projectEntity = new ProjectEntity();
        UserEntity userEntity = userRepository.findById(projectDTO.getIdUser());
        CompanyEntity companyEntity = userEntity.getCompanyEntity();


        if(!String.valueOf(projectDTO.getId()).equals("0")){
            ProjectEntity projectSaved = projectRepository.getById(projectDTO.getId());

            projectEntity.setId(projectDTO.getId());
            projectEntity.setDateCreated(projectSaved.getDateCreated());

            if(multipartFile == null && !projectDTO.getImageId().equals("null")  && projectDTO.getImageUrl().equals("null") ){
                cloudinaryService.deleteFile(projectDTO.getImageId());
                projectEntity.setImageUrl(null);
                projectEntity.setImageId(null);

            }
            else if(!projectDTO.getImageId().equals("null") && multipartFile != null) {
                cloudinaryService.deleteFile(projectDTO.getImageId());
                Map result = cloudinaryService.uploadFile(multipartFile, "/projects");
                projectEntity.setImageUrl(result.get("url").toString());
                projectEntity.setImageId(result.get("public_id").toString());
            }
            else if(multipartFile != null) {
                Map result = cloudinaryService.uploadFile(multipartFile, "/projects");
                projectEntity.setImageUrl(result.get("url").toString());
                projectEntity.setImageId(result.get("public_id").toString());
            }else{
                projectEntity.setImageId(projectSaved.getImageId());
                projectEntity.setImageUrl(projectSaved.getImageUrl());

            }
        }
        else {
            projectEntity.setDateUpdated(LocalDateTime.now());
            projectEntity.setDateCreated(LocalDateTime.now());

            if (multipartFile != null) {
                Map result = cloudinaryService.uploadFile(multipartFile, "/projects");
                projectEntity.setImageId(result.get("public_id").toString());
                projectEntity.setImageUrl(result.get("secure_url").toString());
            }
        }

        projectEntity.setCompanyEntity(companyEntity);
        projectEntity.setName(projectDTO.getName());
        projectEntity.setObjectives(projectDTO.getObjectives());
        projectEntity.setStatus(1);

        projectRepository.save(projectEntity);
    }
}
