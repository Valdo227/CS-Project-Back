package com.csprojectback.freelork.service;

import com.csprojectback.freelork.dto.ProjectDTO;
import com.csprojectback.freelork.dto.ProjectRegistersDTO;
import com.csprojectback.freelork.dto.StudentDTO;
import com.csprojectback.freelork.dto.SummaryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    void updateStudent(MultipartFile file, StudentDTO studentDTO) throws IOException;

    StudentDTO getStudent(int id);

    SummaryDTO getSummary(int id);

    List<ProjectDTO> getProjects(int id);

    void setProject(int idStudent,int idProject);

    void deleteProject(int idUser, int idProject);

    void setCompany(int idUser, int idCompany);

    List<ProjectRegistersDTO> getProjectsCompany(int id);

}
