package co.parrolabs.service;

import co.parrolabs.model.Product;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface IntegrationGateway {

    @Gateway(requestChannel = "integration.gateway.channel")
    String sendMessage(String message);

    @Gateway(requestChannel = "integration.product.gateway.channel")
    String processProductGateway(Product product);
}
