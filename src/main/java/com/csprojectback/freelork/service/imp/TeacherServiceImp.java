package com.csprojectback.freelork.service.imp;

import com.csprojectback.freelork.dto.*;
import com.csprojectback.freelork.entity.*;
import com.csprojectback.freelork.repository.*;
import com.csprojectback.freelork.service.CloudinaryService;
import com.csprojectback.freelork.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImp implements TeacherService {

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
    ClazzRepository clazzRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void updateTeacher(MultipartFile file, TeacherDTO teacherDTO) throws IOException {
        UserEntity userEntity = userRepository.findById(teacherDTO.getIdUser());
        TeacherEntity teacherEntity = userEntity.getTeacherEntity();

        teacherEntity.getUserEntity().setFullName(teacherDTO.getName());
        teacherEntity.setGrade(teacherDTO.getGrade());
        teacherEntity.setPhone(teacherDTO.getPhone());
        teacherEntity.getUserEntity().setEmail(teacherDTO.getEmail());
        teacherEntity.getUserEntity().setDateUpdated(LocalDateTime.now());
        if (!teacherDTO.getPassword().equals("null") && !passwordEncoder.matches(teacherDTO.getPassword(), teacherEntity.getUserEntity().getPassword()))
            teacherEntity.getUserEntity().setPassword(passwordEncoder.encode(teacherDTO.getPassword()));

        if (file != null) {
            if (teacherEntity.getUserEntity().getImageId() != null)
                cloudinaryService.deleteFile(teacherEntity.getUserEntity().getImageId());

            Map result = cloudinaryService.uploadFile(file, "/profile/teacher");
            teacherEntity.getUserEntity().setImageId(result.get("public_id").toString());
            teacherEntity.getUserEntity().setImageUrl(result.get("secure_url").toString());

        }
        /*
        else
            if (studentEntity.getUserEntity().getImageUrl() != null)
                cloudinaryService.deleteFile(studentEntity.getUserEntity().getImageId());

         */
        teacherRepository.save(teacherEntity);

    }

    public SummaryTeacherDTO getSummary(int id) {
        SummaryTeacherDTO summaryTeacherDTO = new SummaryTeacherDTO();

        UserEntity userEntity = userRepository.findById(id);
        TeacherEntity teacherEntity = userEntity.getTeacherEntity();
        List<RegisterTeacherDTO> registerDTOS = new ArrayList<>(),registerSorted = new ArrayList<>();

        int i = 0, tasks = 0, students = 0, groups = 0;


        for(ClassroomEntity classroomEntity : classroomRepository.findByTeacherEntityAndStatusNot(teacherEntity, 0)){
            for(StudentEntity studentEntity: studentRepository.findByClassroomEntity(classroomEntity)){
                for(RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentEntity,0)){
                    RegisterTeacherDTO registerDTO = new RegisterTeacherDTO();
                    registerDTO.setId(registerEntity.getId());
                    registerDTO.setTitle(registerEntity.getTitle());
                    registerDTO.setDateRegister(registerEntity.getDateRegister().format(format));
                    registerDTO.setTimeRegister(registerEntity.getTimeRegister());
                    registerDTO.setStudent(registerEntity.getStudentEntity().getUserEntity().getFullName());
                    registerDTO.setNameCompany(registerEntity.getStudentEntity().getCompanyEntity().getUserEntity().getFullName());
                    registerDTO.setNameProject(registerEntity.getProjectEntity().getName());
                    registerDTO.setStatus(registerEntity.getStatus());

                    registerDTOS.add(registerDTO);
                    tasks++;
                }
                students++;
            }
            groups++;
        }

        registerDTOS = registerDTOS.stream()
                .sorted(Comparator.comparingInt(RegisterTeacherDTO::getId).reversed())
                .collect(Collectors.toList());

        for (RegisterTeacherDTO registerDTO: registerDTOS){
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
            classroomDTO.setStudents(studentRepository.findByClassroomEntity(classroomEntity).size());
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
            for(StudentEntity studentEntity: studentRepository.findByClassroomEntity(classroomEntity)){
                StudentSummaryDTO studentDTO = new StudentSummaryDTO();
                int hours = 0;

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

    @Override
    public List<ClazzDTO> getClazz(String career, int grade, String schedule) {
        List<ClazzDTO> clazzDTOS = new ArrayList<>();
        for(ClazzEntity clazzEntity: clazzRepository.findByCareerNameAndGradeAndSchedule(career,grade,schedule)){
            ClazzDTO clazzDTO = new ClazzDTO();

            clazzDTO.setId(clazzEntity.getId());
            clazzDTO.setName(clazzEntity.getName());

            clazzDTOS.add(clazzDTO);
        }

        return clazzDTOS;
    }

    @Override
    public void createClassroom(ClassroomNewDTO classroomDTO) {
        ClassroomEntity classroomEntity = new ClassroomEntity();
        ClazzEntity clazzEntity = clazzRepository.getById(classroomDTO.getIdClazz());
        TeacherEntity teacherEntity = userRepository.findById(classroomDTO.getIdUser()).getTeacherEntity();
        String code = classroomDTO.getIdClazz() + UserServiceImp.generateCode(10- String.valueOf(classroomDTO.getIdClazz()).length());

        classroomEntity.setName(classroomDTO.getName());
        classroomEntity.setCode(code);
        classroomEntity.setClazzEntity(clazzEntity);
        classroomEntity.setTeacherEntity(teacherEntity);
        classroomEntity.setStatus(1);
        classroomEntity.setDateCreated(LocalDateTime.now());
        classroomEntity.setDateUpdated(LocalDateTime.now());

        classroomRepository.save(classroomEntity);
    }

    @Override
    public void deleteClassroom(int id) {
        ClassroomEntity classroomEntity = classroomRepository.getById(id);
        classroomEntity.setStatus(0);

        classroomRepository.save(classroomEntity);
    }

    @Override
    public StudentFullProfileDTO getStudentProfile(int id) {
        StudentFullProfileDTO studentFullProfileDTO = new StudentFullProfileDTO();
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = userEntity.getStudentEntity();
        StudentProfileDTO studentProfileDTO = new StudentProfileDTO();
        List<ProjectRegistersDTO> projectRegistersDTOS = new ArrayList<>();
        int hours = 0;

        studentProfileDTO.setId(studentEntity.getId());
        studentProfileDTO.setFullName(userEntity.getFullName());
        studentProfileDTO.setEmail(userEntity.getEmail());
        studentProfileDTO.setImage(studentEntity.getUserEntity().getImageUrl());
        studentProfileDTO.setRole(userEntity.getRole());
        studentProfileDTO.setEnrollment(studentEntity.getEnrollment());
        studentProfileDTO.setCompany(studentEntity.getCompanyEntity().getUserEntity().getFullName());
        studentProfileDTO.setCareer(studentEntity.getClassroomEntity().getClazzEntity().getCareerName());

        for (RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusOrderByIdDesc(studentEntity,3)) {
            hours += registerEntity.getTimeRegister();
        }
        studentProfileDTO.setHours(hours);
        StudentServiceImp.setProjects(studentEntity, projectRegistersDTOS, registerRepository);

        studentFullProfileDTO.setStudent(studentProfileDTO);
        studentFullProfileDTO.setProjects(projectRegistersDTOS);

        return studentFullProfileDTO;
    }

    @Override
    public TeacherProfile getProfile(int id) {
        TeacherProfile teacherProfile = new TeacherProfile();
        TeacherEntity teacherEntity = userRepository.findById(id).getTeacherEntity();

        teacherProfile.setId(teacherEntity.getId());
        teacherProfile.setFullName(teacherEntity.getUserEntity().getFullName());
        teacherProfile.setEmail(teacherEntity.getUserEntity().getEmail());
        teacherProfile.setGrade(teacherEntity.getGrade());
        teacherProfile.setPhone(teacherEntity.getPhone());
        teacherProfile.setImage(teacherEntity.getUserEntity().getImageUrl());

        return teacherProfile;
    }
}
