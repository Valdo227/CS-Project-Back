package com.csprojectback.freelork.service;

import com.csprojectback.freelork.dto.*;
import net.minidev.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeacherService {

    void updateTeacher(MultipartFile file, TeacherDTO teacherDTO) throws IOException;

    SummaryTeacherDTO getSummary(int id);

    List<ClassroomTeacherDTO> getClassrooms(int id);

    List<StudentSummaryDTO> getStudents(int id);

    List<ClazzDTO> getClazz(String career, int grade, String schedule);

    void createClassroom(ClassroomNewDTO classroomDTO);

    void deleteClassroom(int id);

    StudentFullProfileDTO getStudentProfile(int id);

    TeacherProfile getProfile(int id);

}
