package co.parrolabs.controller;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.mongodb.CustomerMongoDbDto;
import co.parrolabs.dto.request.CustomerRequest;
import co.parrolabs.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    //@Secured({“ROLE_VIEWER”,”ROLE_EDITOR”}) Same as @PreAuthorize(“hasRole(‘ROLE_VIEWER’)”)
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("{id}")
    public Mono<CustomerDto> getCustomerById(@PathVariable("id") UUID id) {
        return Mono.justOrEmpty(customerService.getCustomerById(id));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerDto> saveCustomer(@RequestBody CustomerRequest customerRequest) {
        return Mono.justOrEmpty(customerService.saveCustomer(customerRequest));
    }

    @DeleteMapping(value = "/deleteCustomer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerMongoDbDto> deleteCustomer(@PathVariable("id") UUID id)  {
        return Mono.justOrEmpty(customerService.deleteCustomer(id));
    }
}
