package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.LoginRequest;

public interface LoginService {
    String doLogin(LoginRequest loginRequest);
}
