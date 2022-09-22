package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.SignupRequest;
import com.pundir.usermanagement.entities.ERole;
import com.pundir.usermanagement.entities.Role;
import com.pundir.usermanagement.entities.User;
import com.pundir.usermanagement.repository.RoleRepository;
import com.pundir.usermanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class SignupServiceImpl implements SignupService {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User doSignup(SignupRequest signupRequest) {
        log.info("signup request.{}",signupRequest);
        Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
        if(role.isEmpty())
            throw new RuntimeException("Role does not exits");

        // Create new user's account
        User user = User.builder()
                .email(signupRequest.getEmail())
                .contact(signupRequest.getContact())
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .password(encoder.encode(signupRequest.getPassword()))
                .roles(Set.of(role.get()))
                .build();
       user =  userRepository.save(user);
        log.info("User successfully save.{}", user.getId());
        return user;
    }
}
