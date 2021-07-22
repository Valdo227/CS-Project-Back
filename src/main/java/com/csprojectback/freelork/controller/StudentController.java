package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.dto.StudentDTO;
import com.csprojectback.freelork.exception.BusinessException;
import com.csprojectback.freelork.model.ViewModel;
import com.csprojectback.freelork.service.StudentService;
import com.fasterxml.jackson.annotation.JsonView;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHENTICATION_BASE;

@RestController
@RequestMapping(URL_PRIVATE_AUTHENTICATION_BASE +"student/")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })
public class StudentController {

    @Autowired
    StudentService studentService;

    @PutMapping("update")
    public JSONObject updateStudent(@RequestParam(name = "file" ,required = false) MultipartFile file, StudentDTO studentDTO){
        try {
            JSONObject json = new JSONObject();
            studentService.updateStudent(file,studentDTO);
            json.put("Status", "200");
            return json;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.EXPECTATION_FAILED, "StudentController");
        }
    }

    @GetMapping("{id}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
    public StudentDTO getStudent(@PathVariable("id") int id){
        return studentService.getStudent(id);
    }
}
