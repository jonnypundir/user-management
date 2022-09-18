package com.pundir.usermanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {

    @GetMapping(value = "/")
    public ResponseEntity<?> get(){
        return new ResponseEntity<>("Welcome To Transol User Mgmt",HttpStatus.OK);
    }
}
