package com.pundir.usermanagement.repository;

import java.util.Optional;

import com.pundir.usermanagement.entities.ERole;
import com.pundir.usermanagement.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
