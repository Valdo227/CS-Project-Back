package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.entity.AdminEntity;
import com.csprojectback.freelork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHETICATION_BASE;

@RestController
@RequestMapping(URL_PRIVATE_AUTHETICATION_BASE+"user/")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })

public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("admin")
    @ResponseBody
    public AdminEntity createAdmin(@RequestBody AdminEntity adminEntity) {

        return userService.createAdmin(adminEntity);
    }
}
