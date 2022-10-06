package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.SignupRequest;
import com.pundir.usermanagement.entities.ERole;
import com.pundir.usermanagement.entities.Role;
import com.pundir.usermanagement.entities.User;
import com.pundir.usermanagement.repository.RoleRepository;
import com.pundir.usermanagement.repository.UserRepository;
import com.pundir.usermanagement.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User register(SignupRequest signupRequest) {
        log.info("Signup request for user {} ",signupRequest.getEmail());
        Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
        if(role.isEmpty())
            throw new RuntimeException("Role does not exits");

        User user = User.builder()
                .email(signupRequest.getEmail())
                .contact(signupRequest.getContact())
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .password(encoder.encode(signupRequest.getPassword()))
                .roles(Set.of(role.get()))
                .build();
       user =  userRepository.save(user);
        log.info("User registered successfully {}", user.getId());
        return user;
    }

    @Override
    @Transactional
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        log.info("fetch user details for user : {} ",email);
        return UserUtils.build(user);
    }
}
