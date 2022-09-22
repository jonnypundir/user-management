package com.pundir.usermanagement.controllers;

import com.pundir.usermanagement.dto.request.LoginRequest;
import com.pundir.usermanagement.dto.request.SignupRequest;
import com.pundir.usermanagement.repository.UserRepository;
import com.pundir.usermanagement.services.LoginService;
import com.pundir.usermanagement.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    SignupService signupService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception{
        ResponseEntity responseEntity = this.loginService.doLogin(loginRequest);
        return responseEntity.ok(responseEntity);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseEntity("Error: Username is already taken!", HttpStatus.BAD_GATEWAY));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseEntity("Error: Email is already in use!", HttpStatus.BAD_GATEWAY));
        }
        this.signupService.doSignup(signupRequest);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
