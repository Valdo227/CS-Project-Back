package com.csprojectback.freelork.service;

import com.csprojectback.freelork.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    void updateStudent(MultipartFile file, StudentDTO studentDTO) throws IOException;

    StudentDTO getStudent(int id);

    StudentProfileDTO getProfile(int id);

    SummaryStudentDTO getSummary(int id);

    List<ProjectDTO> getProjects(int id);

    void setProject(int idStudent,int idProject);

    void deleteProject(int idUser, int idProject);

    void setCompany(int idUser, int idCompany);

    void deleteCompany(int idUser, int idCompany);

    List<ProjectRegistersDTO> getProjectsCompany(int id);

    ClassroomDTO getClassroom(int id);

    void deleteClassroom(int id);

    void setClassroom(int id, String code);

    CompanyDTO getCompany(int id);



}
