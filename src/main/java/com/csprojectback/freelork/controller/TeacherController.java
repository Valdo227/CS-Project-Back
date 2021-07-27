package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.dto.ClassroomTeacherDTO;
import com.csprojectback.freelork.dto.StudentSummaryDTO;
import com.csprojectback.freelork.dto.SummaryTeacherDTO;
import com.csprojectback.freelork.model.ViewModel;
import com.csprojectback.freelork.service.TeacherService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHENTICATION_BASE;

@RestController
@RequestMapping(URL_PRIVATE_AUTHENTICATION_BASE +"teacher/")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @GetMapping("summary/{id}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
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

}
