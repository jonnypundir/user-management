package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.LoginRequest;
import com.pundir.usermanagement.security.jwthelper.JwtUtils;
import com.pundir.usermanagement.security.jwtservices.UserDetailsImpl;
import com.pundir.usermanagement.security.jwtservices.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public String doLogin(LoginRequest loginRequest) {
        log.info("Login request.{} ", loginRequest);
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        UserDetailsImpl userDetails = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(loginRequest.getEmail());
        return jwtUtils.generateJwtToken(userDetails);
    }
}
