package com.pundir.usermanagement.controllers;

import com.pundir.usermanagement.dto.request.LoginRequest;
import com.pundir.usermanagement.dto.request.SignupRequest;
import com.pundir.usermanagement.repository.UserRepository;
import com.pundir.usermanagement.services.AuthService;
import com.pundir.usermanagement.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    UserRegistrationService userRegistrationService;

    @PostMapping("/auth")
    public ResponseEntity<Map<String,String>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(Map.of("access_token", authService.login(loginRequest)));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(signupRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }
        this.userRegistrationService.register(signupRequest);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
