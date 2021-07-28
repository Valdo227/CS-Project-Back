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

    @PostMapping("project")
    public ResponseEntity<Message> createRegister(@RequestParam(name = "file" ,required = false)MultipartFile file, ProjectDTO projectDTO){

        try {
            companyService.CreateProject(file,projectDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }

    }

}
