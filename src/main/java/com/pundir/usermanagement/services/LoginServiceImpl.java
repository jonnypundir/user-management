package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.LoginRequest;
import com.pundir.usermanagement.dto.response.JwtResponse;
import com.pundir.usermanagement.security.jwthelper.JwtUtils;
import com.pundir.usermanagement.security.jwtservices.UserDetailsImpl;
import com.pundir.usermanagement.security.jwtservices.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserDetailsImpl userDetails = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(loginRequest.getEmail());
        return jwtUtils.generateJwtToken(userDetails);
    }
}
