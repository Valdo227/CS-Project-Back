package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.dto.*;
import com.csprojectback.freelork.exception.BusinessException;
import com.csprojectback.freelork.model.ViewModel;
import com.csprojectback.freelork.service.TeacherService;
import com.fasterxml.jackson.annotation.JsonView;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHENTICATION_BASE;

@RestController
@RequestMapping(URL_PRIVATE_AUTHENTICATION_BASE +"teacher/")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PutMapping("update")
    @ResponseBody
    public ResponseEntity<Message> updateStudent(@RequestParam(name = "file" ,required = false) MultipartFile file, TeacherDTO teacherDTO){
        try {
            teacherService.updateTeacher(file,teacherDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }
    }

    @GetMapping("summary/{id}")
    @ResponseBody
    public SummaryTeacherDTO getSummary(@PathVariable("id") int id){
        return teacherService.getSummary(id);
    }

    @GetMapping("classrooms/{id}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
    public List<ClassroomTeacherDTO> getClassrooms(@PathVariable("id") int id){
        return teacherService.getClassrooms(id);
    }

    @GetMapping("students/{id}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
    public List<StudentSummaryDTO> getStudents(@PathVariable("id") int id){
        return teacherService.getStudents(id);
    }

    @GetMapping("class/{career}/{grade}/{schedule}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
    public List<ClazzDTO> getClazz(@PathVariable("career") String career, @PathVariable("grade") int grade, @PathVariable("schedule") String schedule){
        return teacherService.getClazz(career,grade,schedule);
    }

    @PostMapping("classroom")
    @ResponseBody
    public ResponseEntity<Message> createClassroom(@RequestBody ClassroomNewDTO classroomNewDTO){
        try {
            teacherService.createClassroom(classroomNewDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }
    }

    @PutMapping("delete/classroom/{id}")
    @ResponseBody
    public ResponseEntity<Message> deleteClassroom(@PathVariable("id") int id){
        try {
            teacherService.deleteClassroom(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }
    }

    @GetMapping("student/profile/{id}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
    public StudentFullProfileDTO getStudentProfile(@PathVariable("id") int id){
        return teacherService.getStudentProfile(id);
    }

    @GetMapping("profile/{id}")
    @ResponseBody
    public TeacherProfile getProfile(@PathVariable("id") int id){
        return teacherService.getProfile(id);
    }

}
