package co.parrolabs.httpclient.feign;



import co.parrolabs.dto.AdditionalInfoForOrderResponse;
import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.mongodb.CustomerMongoDbDto;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;
import co.parrolabs.dto.request.CustomerRequest;
import co.parrolabs.dto.request.ProductRequest;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;


@FeignClient( name = "clientFeignServiceModelsCustomer", url="${service-models.url}" )
public interface ClientFeignServiceModelsCustomer
{
    /*********************************Customer controller ****************************************/

    @GetMapping(value = "/customer/{id}")
    CustomerDto getCustomerById(@PathVariable("id") UUID id);

    @PostMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    CustomerDto saveCustomer(@RequestBody CustomerRequest customerRequest);

    @DeleteMapping(value = "/customer/deleteCustomer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    CustomerMongoDbDto deleteCustomer(@PathVariable("id") UUID id);

}