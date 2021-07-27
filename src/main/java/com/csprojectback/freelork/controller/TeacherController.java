package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.dto.SummaryDTO;
import com.csprojectback.freelork.dto.SummaryTeacherDTO;
import com.csprojectback.freelork.model.ViewModel;
import com.csprojectback.freelork.service.StudentService;
import com.csprojectback.freelork.service.TeacherService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
