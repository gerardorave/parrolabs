package co.parrolabs.client.httpclient.feign;



import co.parrolabs.dto.AdditionalInfoForOrderResponse;
import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.mongodb.OrderCustomerMongoDbDto;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;
import co.parrolabs.dto.request.OrderCustomerRequest;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@FeignClient( name = "clientFeignServiceOrderCustomer", url="${service-order-customer.url}" )
public interface ClientFeignServiceOrderCustomer
{

    @GetMapping("/orderCustomer/{id}")
    OrderCustomerDto getOrderCustomerById(@PathVariable("id") UUID id);

    @GetMapping(value = "/orderCustomer/allActiveOrdersByCustomer/{customer_id}")
    List<OrderCustomerDto> getAllOrdersByCustomer(@PathVariable("customer_id") UUID customerId);

    @PostMapping(value = "/orderCustomer", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderCustomerDto saveOrdersCustomer(@RequestBody OrderCustomerRequest orderCustomerRequest);


    @DeleteMapping(value = "/orderCustomer/deleteOrderById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderCustomerMongoDbDto deleteProductById(@PathVariable("id") UUID id);

    @DeleteMapping(value = "/orderCustomer/deleteOrderByOrderNumber/{order_number}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderCustomerMongoDbDto deleteProductByOrderNumber(@PathVariable("order_number") Long orderNumber);

}