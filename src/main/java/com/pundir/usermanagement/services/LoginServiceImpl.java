package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.LoginRequest;
import com.pundir.usermanagement.dto.response.JwtResponse;
import com.pundir.usermanagement.security.jwthelper.JwtUtils;
import com.pundir.usermanagement.security.jwtservices.UserDetailsImpl;
import com.pundir.usermanagement.security.jwtservices.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> doLogin(LoginRequest loginRequest) {
        try{
            this.authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }       UserDetailsImpl userDetails = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
}
