package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.dto.RegisterDTO;
import com.csprojectback.freelork.exception.BusinessException;
import com.csprojectback.freelork.model.ViewModel;
import com.csprojectback.freelork.service.RegisterService;
import com.fasterxml.jackson.annotation.JsonView;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public JSONObject createRegister(@RequestParam(name = "file" ,required = false)MultipartFile file, RegisterDTO registerDTO){

        try {
            JSONObject json = new JSONObject();
            registerService.createRegister(file,registerDTO);
            json.put("Status", "200");
            return json;
        } catch (Exception e) {
            throw new BusinessException("Ha ocurrido un error", HttpStatus.EXPECTATION_FAILED, "RegisterController");
        }

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

}
