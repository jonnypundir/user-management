package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.SignupRequest;
import com.pundir.usermanagement.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserRegistrationService {
    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;
    User register(SignupRequest signupRequest);
}
