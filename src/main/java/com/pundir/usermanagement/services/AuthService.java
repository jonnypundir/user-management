package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.LoginRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);
}
