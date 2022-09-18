package com.pundir.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import com.pundir.usermanagement.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);
  Boolean existsByUsername(String username);
  @Query("{roles : {$ne : ?0} }")
  List<User> getNonAdminUser(String role);
  Boolean existsByEmail(String email);
}
