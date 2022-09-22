package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.SignupRequest;
import com.pundir.usermanagement.entities.User;

public interface SignupService {
    User doSignup(SignupRequest signupRequest);
}
