package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.SignupRequest;
import com.pundir.usermanagement.entities.ERole;
import com.pundir.usermanagement.entities.Role;
import com.pundir.usermanagement.entities.User;
import com.pundir.usermanagement.repository.RoleRepository;
import com.pundir.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SignupServiceImpl implements SignupService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public User doSignup(SignupRequest signupRequest) {

        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() ->
                new RuntimeException("Error: Role is not found."));                                            ;
        Set<Role> rolesSet = new HashSet<>();
        rolesSet.add(userRole);
        // Create new user's account
        User user = User.builder()
                .email(signupRequest.getEmail())
                .contact(signupRequest.getContact())
                .username(signupRequest.getUsername())
                .password(encoder.encode(signupRequest.getPassword()))
                .roles(rolesSet)
                .build();
        System.out.println(user);
        userRepository.save(user);
        return user;
    }
}
