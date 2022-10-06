package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.AddressRequest;
import com.pundir.usermanagement.entities.Address;
import com.pundir.usermanagement.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressRegistrationServiceImpl implements AddressRegistrationService {

    @Autowired AddressRepository addressRepository;

    @Override
    public Address saveAddress(AddressRequest addressRequest) {
        log.info("Address request");
        Address address = Address.builder()
                .email("abhi@gmail.com")
                .address(addressRequest.getAddress())
                .state(addressRequest.getState())
                .city(addressRequest.getCity())
                .pin(addressRequest.getPin())
                .contact(addressRequest.getContact())
                .build();
        address = addressRepository.save(address);
        log.info("User's address registered successfully {}", address.getId());
        return address;
    }
}
