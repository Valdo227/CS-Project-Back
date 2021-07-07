package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.entity.Tokenz;
import com.csprojectback.freelork.entity.UserEntity;
import com.csprojectback.freelork.repository.UserRepository;
import com.csprojectback.freelork.service.AuthService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS })
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/freelork/login")
    public JSONObject login(@RequestParam("email") String email, @RequestParam("pass") String pwd) {
        Tokenz tokenz = new Tokenz();
        UserEntity userEntity = new UserEntity();

        tokenz.setToken(authService.loginAuthentication(email, pwd));

        if (!tokenz.getToken().equals("")) {
            userEntity = userRepository.findByEmail(email).orElse(null);
        }

        JSONObject jsonObject = new JSONObject();
        JSONObject usuario = new JSONObject();
        jsonObject.put("permissions", new JSONArray());
        usuario.put("role", userEntity.getRole());
        usuario.put("email", email);
        jsonObject.put("user", usuario);
        jsonObject.put("token", tokenz.getToken());
        return jsonObject;
    }

}
