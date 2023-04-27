package co.parrolabs.service;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.mongodb.CustomerMongoDbDto;
import co.parrolabs.dto.request.CustomerRequest;

import java.util.UUID;

public interface CustomerService {

    CustomerDto getCustomerById(UUID id);
    CustomerMongoDbDto deleteCustomer(UUID id);
    CustomerDto saveCustomer(CustomerRequest customerRequest);
}
