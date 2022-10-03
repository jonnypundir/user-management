package com.pundir.usermanagement.services;

import com.pundir.usermanagement.dto.request.AddressRequest;
import com.pundir.usermanagement.entities.Address;

public interface AddressRegistrationService {
    Address saveAddress(AddressRequest addressRequest);
}
