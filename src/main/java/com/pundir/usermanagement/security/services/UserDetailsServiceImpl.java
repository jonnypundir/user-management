package com.pundir.usermanagement.security.services;

import com.pundir.usermanagement.entities.User;
import com.pundir.usermanagement.repository.UserRepository;
import com.pundir.usermanagement.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
    UserRepository userRepository;

	@Autowired
	UserRepositoryImpl userRepositoryImpl;

	@Value("${non.admin.users:ROLE_ADMIN}")
	private String nonAdminUsers;


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

	public List<User> findAll(){
		return userRepository.findAll();
	}

	public List<User> findAllNonAdminUser(){
		List<User> users= userRepositoryImpl.getNonAdminUser(nonAdminUsers);
		System.out.println("users "+users);
		return users;
	}
}
