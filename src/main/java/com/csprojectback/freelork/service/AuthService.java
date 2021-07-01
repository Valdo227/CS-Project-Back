package com.csprojectback.freelork.service;

public interface AuthService {

    /**
     * JWT Authentication and generation
     *
     * @param email email
     * @param pwd password
     * @return JWT
     */
    String loginAuthentication(String email, String pwd);

}
