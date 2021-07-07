package com.csprojectback.freelork.service;

import com.csprojectback.freelork.entity.AdminEntity;
import com.csprojectback.freelork.entity.TeacherEntity;

public interface UserService {

    AdminEntity createAdmin(AdminEntity adminEntity);

    TeacherEntity createTeacher(TeacherEntity teacherEntity);

}
