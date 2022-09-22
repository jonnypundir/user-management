package com.pundir.usermanagement.controllers;

import com.pundir.usermanagement.dto.request.LoginRequest;
import com.pundir.usermanagement.dto.request.SignupRequest;
import com.pundir.usermanagement.dto.response.JwtResponse;
import com.pundir.usermanagement.entities.ERole;
import com.pundir.usermanagement.entities.Role;
import com.pundir.usermanagement.entities.User;
import com.pundir.usermanagement.repository.RoleRepository;
import com.pundir.usermanagement.repository.UserRepository;
import com.pundir.usermanagement.security.jwthelper.JwtUtils;
import com.pundir.usermanagement.security.jwtservices.UserDetailsImpl;
import com.pundir.usermanagement.security.jwtservices.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            this.authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (UsernameNotFoundException ex){
            System.err.println(ex.getMessage());
        }catch (BadCredentialsException exception){
            exception.printStackTrace();
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(loginRequest.getUsername());
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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseEntity("Error: Username is already taken!", HttpStatus.BAD_GATEWAY));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseEntity("Error: Email is already in use!", HttpStatus.BAD_GATEWAY));
        }
        Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
        if(role.isEmpty())
            throw new RuntimeException("Role does not exits");
        // Create new user's account
        User user = User.builder()
                .email(signupRequest.getEmail())
                .contact(signupRequest.getContact())
                .username(signupRequest.getUsername())
                .password(encoder.encode(signupRequest.getPassword()))
                .roles(Set.of(role.get()))
                .build();

        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
