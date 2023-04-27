package co.parrolabs.controller;

import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.mongodb.OrderCustomerMongoDbDto;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.OrderCustomerRequest;

import co.parrolabs.service.OrderCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orderCustomer")
public class OrderCustomerController {

    private OrderCustomerService orderCustomerService;

    @Autowired
    public OrderCustomerController(OrderCustomerService orderCustomerService) {
        this.orderCustomerService = orderCustomerService;
    }

    @GetMapping("{id}")
    public Optional<OrderCustomerDto> getOrderCustomerById(@PathVariable("id") UUID id) {

        return orderCustomerService.findById(id);
    }

    @GetMapping(value = "/allActiveOrdersByCustomer/{customer_id}")
    public Flux<List<OrderCustomerDto>> getAllOrdersByCustomer(@PathVariable("customer_id") UUID customerId) {

        return Flux.empty();
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<OrderCustomerDto> saveOrdersCustomer(@RequestBody @Valid OrderCustomerRequest orderCustomerRequest) {
        OrderCustomerDto orderCustomerDto = orderCustomerService.save(orderCustomerRequest);
        return Mono.justOrEmpty(orderCustomerDto);
    }

    @DeleteMapping(value = "/deleteOrderById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<OrderCustomerMongoDbDto> deleteProductById(@PathVariable("id") UUID id)  {
        OrderCustomerMongoDbDto productMongoDbDto = orderCustomerService.deleteById(id);
        return Mono.justOrEmpty(productMongoDbDto);
    }

    @DeleteMapping(value = "/deleteOrderByOrderNumber/{order_number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<OrderCustomerMongoDbDto> deleteProductByOrderNumber(@PathVariable("order_number") Long orderNumber)  {
        OrderCustomerMongoDbDto productMongoDbDto = orderCustomerService.deleteByOrderNumber(orderNumber);
        return Mono.justOrEmpty(productMongoDbDto);
    }
}
