package co.parrolabs.controller;


import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.mongodb.OrderCustomerMongoDbDto;
import co.parrolabs.dto.request.OrderCustomerRequest;
import co.parrolabs.service.OrderCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<OrderCustomerDto> getOrderCustomerById(@PathVariable("id") UUID id) {

        return Mono.justOrEmpty(orderCustomerService.findById(id));
    }

    @GetMapping(value = "/allActiveOrdersByCustomer/{customer_id}")
    public Flux<List<OrderCustomerDto>> getAllOrdersByCustomer(@PathVariable("customer_id") UUID customerId) {

        return Flux.fromStream( orderCustomerService.getAllOrdersByCustomer(customerId));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<OrderCustomerDto> saveOrdersCustomer(@RequestBody OrderCustomerRequest orderCustomerRequest) {
        return Mono.justOrEmpty(orderCustomerService.save(orderCustomerRequest));
    }

    @DeleteMapping(value = "/deleteOrderById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<OrderCustomerMongoDbDto> deleteProductById(@PathVariable("id") UUID id)  {
        return Mono.justOrEmpty(orderCustomerService.deleteById(id));
    }

    @DeleteMapping(value = "/deleteOrderByOrderNumber/{order_number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<OrderCustomerMongoDbDto> deleteProductByOrderNumber(@PathVariable("order_number") Long orderNumber)  {
        return Mono.justOrEmpty(orderCustomerService.deleteByOrderNumber(orderNumber));
    }
}
