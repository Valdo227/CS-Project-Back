package com.csprojectback.freelork.service;

import com.csprojectback.freelork.dto.ClassroomTeacherDTO;
import com.csprojectback.freelork.dto.StudentSummaryDTO;
import com.csprojectback.freelork.dto.SummaryTeacherDTO;

import java.util.List;

public interface TeacherService {

    SummaryTeacherDTO getSummary(int id);

    List<ClassroomTeacherDTO> getClassrooms(int id);

    List<StudentSummaryDTO> getStudents(int id);
}
