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

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") UUID id) {
        CustomerDto customerDtoResponse = customerService.getCustomerById(id);
        return new ResponseEntity<>(customerDtoResponse, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> saveCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        CustomerDto customerDtoResponse = customerService.saveCustomer(customerRequest);
        return new ResponseEntity<>(customerDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteCustomer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerMongoDbDto> deleteCustomer(@PathVariable("id") UUID id)  {

        CustomerMongoDbDto customerMongoDbDto =  customerService.deleteCustomer(id);
        return new ResponseEntity<>(customerMongoDbDto, HttpStatus.OK);
    }
}
