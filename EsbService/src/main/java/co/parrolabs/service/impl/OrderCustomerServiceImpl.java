package co.parrolabs.service.impl;

import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.mongodb.OrderCustomerMongoDbDto;
import co.parrolabs.dto.request.OrderCustomerRequest;
import co.parrolabs.client.httpclient.feign.ClientFeignServiceOrderCustomer;
import co.parrolabs.service.OrderCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class OrderCustomerServiceImpl implements OrderCustomerService {

    private ClientFeignServiceOrderCustomer clientFeignServiceOrderCustomer;

    @Autowired
    public OrderCustomerServiceImpl(ClientFeignServiceOrderCustomer clientFeignServiceOrderCustomer

    ) {
        this.clientFeignServiceOrderCustomer = clientFeignServiceOrderCustomer;
    }


    public Optional<OrderCustomerDto> save(OrderCustomerRequest orderCustomerRequest) {
        return Optional.of(clientFeignServiceOrderCustomer.saveOrdersCustomer(orderCustomerRequest));
    }
    @Override
    public Optional<OrderCustomerDto> findById(UUID id) {
        return Optional.of(clientFeignServiceOrderCustomer.getOrderCustomerById(id));
    }

    @Override
    public Optional<OrderCustomerMongoDbDto> deleteById(UUID id) {

        return Optional.of(clientFeignServiceOrderCustomer.deleteProductById(id));
    }

    @Override
    public Optional<OrderCustomerMongoDbDto> deleteByOrderNumber(Long orderNumber) {
        return Optional.of(clientFeignServiceOrderCustomer.deleteProductByOrderNumber(orderNumber));
    }
    @Override
    public Stream<List<OrderCustomerDto>> getAllOrdersByCustomer(UUID customerId) {
        return Stream.of(clientFeignServiceOrderCustomer.getAllOrdersByCustomer(customerId));

    }

}
