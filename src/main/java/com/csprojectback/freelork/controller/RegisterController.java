package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.dto.Message;
import com.csprojectback.freelork.dto.RegisterDTO;
import com.csprojectback.freelork.model.ViewModel;
import com.csprojectback.freelork.service.RegisterService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHENTICATION_BASE;

@RestController
@RequestMapping(URL_PRIVATE_AUTHENTICATION_BASE +"register/")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @PostMapping("")
    public ResponseEntity<Message> createRegister(@RequestParam(name = "file" ,required = false)MultipartFile file, RegisterDTO registerDTO){

        try {

            registerService.createRegister(file,registerDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }

    }

    @GetMapping("{id}")
    @ResponseBody
    public RegisterDTO getRegister(@PathVariable("id") int id){
        return registerService.getRegister(id);
    }

    @GetMapping("list/{id}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
    public List<RegisterDTO> getRegisterList(@PathVariable("id") int id){
        return registerService.getRegisterList(id);
    }

    @GetMapping("list/{id}/{date1}/{date2}")
    @ResponseBody
    @JsonView(ViewModel.Internal.class)
    public List<RegisterDTO> getRegisterList(@PathVariable("id") int id, @PathVariable("date1") String date1, @PathVariable("date2") String date2){
        return registerService.getRegisterListDate(id,date1,date2);
    }

    @PutMapping("delete/{id}")
    @ResponseBody
    public ResponseEntity<Message> deleteRegister(@PathVariable("id") int id){
        try {
            registerService.deleteRegister(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }
    }

}
