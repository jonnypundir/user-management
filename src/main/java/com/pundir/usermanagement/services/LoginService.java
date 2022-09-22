package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<?> doLogin(LoginRequest loginRequest);
}
