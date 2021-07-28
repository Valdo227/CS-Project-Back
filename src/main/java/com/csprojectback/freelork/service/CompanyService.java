package com.csprojectback.freelork.service;

import com.csprojectback.freelork.dto.*;
import com.csprojectback.freelork.entity.ProjectEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyService {

    void updateCompany(MultipartFile file, CompanyDTO companyDTO) throws IOException;

    SummaryCompanyDTO getSummary(int id);

    List<ProjectDTO> getProjects(int id);

    ProjectDTO getProject(int id);

    void CreateProject(MultipartFile multipartFile, ProjectDTO projectDTO) throws IOException;

    void deleteProject(int id);

    List<RegisterCompanyDTO> getRegisterList(int id);

    List<RegisterCompanyDTO> getRegisterListDate(int id, String date1, String date2);

    void ChangeRegisterStatus(int id,int status);

    List<CompanyStudentsDTO> getStudents(int id);

    CompanyProfileDTO getProfile(int id);
}
