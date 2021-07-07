package com.csprojectback.freelork.controller;

import org.springframework.web.bind.annotation.*;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHENTICATION_BASE;

@RestController
@RequestMapping(URL_PRIVATE_AUTHENTICATION_BASE +"user/")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })

public class UserController {


}
