package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.SignupRequest;
import com.pundir.usermanagement.entities.User;
import com.pundir.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupServiceImpl implements SignupService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;

    @Override
    public User doSignup(SignupRequest signupRequest) {
        // Create new user's account
        User user = User.builder()
                .email(signupRequest.getEmail())
                .contact(signupRequest.getContact())
                .username(signupRequest.getUsername())
                .password(encoder.encode(signupRequest.getPassword()))
                //.roles(Set.of(new Role("",ERole.ROLE_USER)))
                .build();
        System.out.println(user);
        userRepository.save(user);
        return user;
    }
}
