package co.parrolabs.service;

import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.mongodb.OrderCustomerMongoDbDto;
import co.parrolabs.dto.request.OrderCustomerRequest;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface OrderCustomerService {

    Optional<OrderCustomerDto> save(OrderCustomerRequest orderCustomerRequest);
    Optional<OrderCustomerDto> findById(UUID id);
    Optional<OrderCustomerMongoDbDto> deleteById(UUID id);
    Optional<OrderCustomerMongoDbDto> deleteByOrderNumber(Long orderNumber);
    Stream<List<OrderCustomerDto>> getAllOrdersByCustomer(UUID customerId);
}
