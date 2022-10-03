package com.pundir.usermanagement.repository;

import com.pundir.usermanagement.entities.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {
}
