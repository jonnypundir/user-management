package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.UserDetailsDto;
import com.pundir.usermanagement.dto.request.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Override
    public String login(LoginRequest loginRequest) {
        log.info("Login request for user {} ", loginRequest.getEmail());
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        UserDetailsDto userDetails = (UserDetailsDto) userDetailsService.loadUserByUsername(loginRequest.getEmail());
        return jwtService.generateJwtToken(userDetails);
    }
}
