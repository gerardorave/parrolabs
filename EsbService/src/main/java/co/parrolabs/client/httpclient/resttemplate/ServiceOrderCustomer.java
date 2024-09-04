package co.parrolabs.client.httpclient.resttemplate;

import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.mongodb.OrderCustomerMongoDbDto;
import co.parrolabs.dto.request.OrderCustomerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ServiceOrderCustomer {
    @Value("${service-order-customer.url}")
    private String orderCustomerUrl;

    private final RestTemplate restTemplate;

    public ServiceOrderCustomer(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    @PostConstruct
    public void configureRestTemplate() {
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(orderCustomerUrl));
    }

    public OrderCustomerDto getOrderCustomerById(final UUID id){
        final String url = "/orderCustomer/" + id;
        try {
            return restTemplate
                    .exchange(url, HttpMethod.GET,
                            HttpEntity.EMPTY//new HttpEntity<>(basicAuthorizationHeaders(userServiceUser, userServicePassword)),
                            ,new ParameterizedTypeReference<OrderCustomerDto>() {
                            })
                    .getBody();
        } catch (HttpClientErrorException.BadRequest hceebr) {
            log.error("Unable to find orderCustomerBy Id", hceebr.getCause());
            return null;
        }
    }

    @Cacheable(value = "allOrdersByCustomer", key = "#customerId", unless = "#result == null")
    public List<OrderCustomerDto> getAllOrdersByCustomer(final UUID customerId) {
        final String url = "/orderCustomer/allActiveOrdersByCustomer/" + customerId;
        try {
            return restTemplate
                    .exchange(url, HttpMethod.GET,
                            HttpEntity.EMPTY,//new HttpEntity<>(basicAuthorizationHeaders(userServiceUser, userServicePassword)),
                            new ParameterizedTypeReference<List<OrderCustomerDto>>() {
                            })
                    .getBody();
        }
        catch (HttpClientErrorException.BadRequest hceebr) {
            log.error("Unable to get All shipper Accounts by customer {}, Error: {}", customerId, hceebr.getCause());
            return Collections.emptyList();
        }
        catch(RestClientException restClientException) {
            log.error("Unable to get All shipper Accounts by customer {}, Error: {}", customerId, restClientException.getCause());
            return Collections.emptyList();
        }
    }
    public OrderCustomerDto saveOrdersCustomer(final OrderCustomerRequest orderCustomerRequest) {
        final String url ="/orderCustomer";

        final HttpHeaders headers = new HttpHeaders();//basicAuthorizationHeaders(carrierServiceUser, carrierServicePassword);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

        //Combine headers and request body into single holder object
        HttpEntity<OrderCustomerRequest> requestHttpEntity = new HttpEntity<>(orderCustomerRequest, headers);

        try {
            return restTemplate.exchange(url, HttpMethod.POST, requestHttpEntity, OrderCustomerDto.class).getBody();
        } catch (HttpClientErrorException.BadRequest hceebr) {
            log.error("Unable to save Order customer:{}", hceebr.getCause());
            return null;
        }
    }

    public OrderCustomerMongoDbDto deleteProductById(final UUID id) {
        final String url ="/orderCustomer/deleteOrderById/"+id;

        final HttpHeaders headers = new HttpHeaders();//basicAuthorizationHeaders(carrierServiceUser, carrierServicePassword);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        //Combine headers and request body into single holder object
        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, OrderCustomerMongoDbDto.class).getBody();
        } catch (HttpClientErrorException.BadRequest hceebr) {
            log.error("Unable to delete Order customer:{}", hceebr.getCause());
            return null;
        }
    }

}
