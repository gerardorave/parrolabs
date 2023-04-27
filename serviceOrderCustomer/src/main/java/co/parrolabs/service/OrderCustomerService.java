package co.parrolabs.service;

import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.mongodb.OrderCustomerMongoDbDto;

import co.parrolabs.dto.request.OrderCustomerRequest;
import co.parrolabs.model.OrderCustomer;


import java.util.Optional;
import java.util.UUID;

public interface OrderCustomerService {

    OrderCustomerDto save(OrderCustomerRequest orderCustomerRequest);
    Optional<OrderCustomerDto> findById(UUID id);
    OrderCustomerMongoDbDto deleteById(UUID id);
    OrderCustomerMongoDbDto deleteByOrderNumber(Long orderNumber);
}
