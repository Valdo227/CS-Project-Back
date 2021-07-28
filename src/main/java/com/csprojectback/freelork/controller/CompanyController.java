package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.dto.*;
import com.csprojectback.freelork.model.ViewModel;
import com.csprojectback.freelork.service.CompanyService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHENTICATION_BASE;

@RestController
@RequestMapping(URL_PRIVATE_AUTHENTICATION_BASE +"company/")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })

public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PutMapping("update")
    @ResponseBody
    public ResponseEntity<Message> updateCompany(@RequestParam(name = "file" ,required = false) MultipartFile file, CompanyDTO companyDTO){
        try {
            companyService.updateCompany(file, companyDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }
    }

    @GetMapping("summary/{id}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
    public SummaryCompanyDTO getSummary(@PathVariable("id") int id){
        return companyService.getSummary(id);
    }

    @GetMapping("projects/{id}")
    @ResponseBody
    public List<ProjectDTO> getProjects(@PathVariable("id") int id){
        return companyService.getProjects(id);
    }

    @GetMapping("project/{id}")
    @ResponseBody
    public ProjectDTO getProject(@PathVariable("id") int id){
        return companyService.getProject(id);
    }

    @PostMapping("project")
    public ResponseEntity<Message> createRegister(@RequestParam(name = "file" ,required = false)MultipartFile file, ProjectDTO projectDTO){

        try {
            companyService.CreateProject(file,projectDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }

    }

    @PutMapping("delete/project/{id}")
    @ResponseBody
    public ResponseEntity<Message> deleteRegister(@PathVariable("id") int id){
        try {
            companyService.deleteProject(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }
    }

    @GetMapping("students/{id}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
    public List<CompanyStudentsDTO> getStudents(@PathVariable("id") int id){
        return companyService.getStudents(id);
    }

    @PutMapping("register/{id}/status/{status}")
    @ResponseBody
    public ResponseEntity<Message> changeRegisterStatus(@PathVariable("id") int id,@PathVariable("status") int status){
        try {
            companyService.ChangeRegisterStatus(id, status);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }

    }

    @GetMapping("registers/{id}")
    @ResponseBody
    public List<RegisterCompanyDTO> getRegisters(@PathVariable("id") int id){
        return companyService.getRegisters(id);
    }

    @GetMapping("profile/{id}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
    public CompanyProfileDTO getProfile(@PathVariable("id") int id){
        return companyService.getProfile(id);
    }

}
