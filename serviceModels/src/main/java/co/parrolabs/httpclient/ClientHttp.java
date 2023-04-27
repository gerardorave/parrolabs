package co.parrolabs.httpclient;

import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.OrdersDtoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ClientHttp {

    @Value("${service-order-customer.url}")
    private String orderCustomerServiceUrl;

    private String ERROR_MESSAGE = "Error running httpClient";
    private final RestTemplate restTemplate;

    public ClientHttp(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    @PostConstruct
    public void configureRestTemplate() {
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(orderCustomerServiceUrl));
    }

    public List<OrderCustomerDto> findOrdersByCustomerId(String customerId) {
        final String url = "/orderCustomer/ordersByCustomerId/" + customerId;
        try {
            OrdersDtoResponse response = restTemplate.getForObject(url,
                    OrdersDtoResponse.class);
            return response.getOrders();
        } catch (HttpClientErrorException.BadRequest hceebr) {
            log.error("Unable to get the orders with customerId:{}, returning null", customerId);
            return null;
        }
    }

    public List<OrderCustomerDto> findOrdersByProductId(String productId) {
        final String url = "/orderCustomer/ordersByCustomerId/" + productId;
        try {
            OrdersDtoResponse response = restTemplate.getForObject(url,
                    OrdersDtoResponse.class);
            return response.getOrders();
        } catch (HttpClientErrorException.BadRequest hceebr) {
            log.error("Unable to get the orders with productId:{}, returning null", productId);
            return null;
        }
    }
}
