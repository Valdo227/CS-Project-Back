package com.csprojectback.freelork.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.csprojectback.freelork.entity.UserEntity;
import com.csprojectback.freelork.exception.BusinessException;
import com.csprojectback.freelork.repository.UserRepository;
import com.csprojectback.freelork.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Optional;

@Service
@Log4j2
public class AuthServiceImp implements AuthService {

    @Value("${spring.security.oauth2.resource.jwt.key-value}")
    private RSAPublicKey publicKey;

    @Value("${spring.security.jwt.token.prefix}")
    private String tokenPrefix;

    @Value("${spring.security.jwt.expiration.time}")
    private Long expirationTime;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String loginAuthentication(String email, String rawPassword) {
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            // 401 Unauthorized
            throw new BusinessException("Access is denied due to invalid credentials.", HttpStatus.UNAUTHORIZED, "AuthenticationController");
        }

        String encodedPassword = user.get().getPassword();
        boolean isAuthenticated = passwordEncoder.matches(rawPassword, encodedPassword);
        //boolean isAuthenticated = rawPassword.equals(encodedPassword);

        if (!isAuthenticated) {
            // 401 Unauthorized
            throw new BusinessException("Access is denied due to invalid credentials.", HttpStatus.UNAUTHORIZED, "AuthenticationController");
        }

        String token = JWT.create().withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(publicKey.getEncoded()));

        return tokenPrefix + token;
    }

}
