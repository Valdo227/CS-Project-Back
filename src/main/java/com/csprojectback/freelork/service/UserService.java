package com.csprojectback.freelork.service;

import com.csprojectback.freelork.entity.AdminEntity;
import com.csprojectback.freelork.entity.CompanyEntity;
import com.csprojectback.freelork.entity.StudentEntity;
import com.csprojectback.freelork.entity.TeacherEntity;

public interface UserService {

    AdminEntity createAdmin(AdminEntity adminEntity);

    TeacherEntity createTeacher(TeacherEntity teacherEntity);

    StudentEntity createStudent(StudentEntity studentEntity);

    CompanyEntity createCompany(CompanyEntity companyEntity);

}
