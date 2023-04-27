package co.parrolabs.service;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.mongodb.CustomerMongoDbDto;
import co.parrolabs.dto.request.CustomerRequest;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<CustomerDto> getCustomerById(UUID id);
    Optional<CustomerMongoDbDto> deleteCustomer(UUID id);
    Optional<CustomerDto> saveCustomer(CustomerRequest customerRequest);
}
