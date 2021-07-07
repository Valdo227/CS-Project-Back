package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.entity.*;
import com.csprojectback.freelork.exception.BusinessException;
import com.csprojectback.freelork.model.ViewModel;
import com.csprojectback.freelork.repository.UserRepository;
import com.csprojectback.freelork.service.AuthService;
import com.csprojectback.freelork.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHENTICATION;

@RestController
@RequestMapping(URL_PRIVATE_AUTHENTICATION)
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS })
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("login")
    public JSONObject login(@RequestParam("email") String email, @RequestParam("pass") String pwd) {
        Tokenz tokenz = new Tokenz();
        UserEntity userEntity = new UserEntity();

        tokenz.setToken(authService.loginAuthentication(email, pwd));

        if (!tokenz.getToken().equals("")) {
            userEntity = userRepository.findByEmail(email).orElse(null);
        }

        if(userEntity == null)
            throw new BusinessException("Email not found.", HttpStatus.UNAUTHORIZED, "AuthenticationController");

        JSONObject response = new JSONObject();
        JSONObject user = new JSONObject();

        user.put("id", userEntity.getId());
        user.put("role", userEntity.getRole());
        user.put("email", email);
        response.put("user", user);
        response.put("token", tokenz.getToken());

        return response;
    }

    @PostMapping("register/admin")
    @JsonView(ViewModel.Internal.class)
    @ResponseBody
    public AdminEntity createAdmin(@RequestBody AdminEntity adminEntity) {

        return userService.createAdmin(adminEntity);
    }

    @PostMapping("register/teacher")
    @JsonView(ViewModel.Internal.class)
    @ResponseBody
    public TeacherEntity createTeacher(@RequestBody TeacherEntity teacherEntity) {

        return userService.createTeacher(teacherEntity);
    }

    @PostMapping("register/company")
    @JsonView(ViewModel.Internal.class)
    @ResponseBody
    public CompanyEntity createAdmin(@RequestBody CompanyEntity companyEntity) {

        return userService.createCompany(companyEntity);
    }

    @PostMapping("register/student")
    @JsonView(ViewModel.Internal.class)
    @ResponseBody
    public StudentEntity createAdmin(@RequestBody StudentEntity studentEntity) {

        return userService.createStudent(studentEntity);
    }

}
