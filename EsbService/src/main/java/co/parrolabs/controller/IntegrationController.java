package co.parrolabs.controller;

import co.parrolabs.dto.request.ProductRequest;
import co.parrolabs.model.Product;
import co.parrolabs.service.IntegrationGateway;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integrate")
public class IntegrationController {

    private IntegrationGateway integrationGateway;

    private ModelMapper mapper;

    @Autowired
    public IntegrationController(IntegrationGateway integrationGateway, ModelMapper mapper) {
        this.integrationGateway = integrationGateway;
        this.mapper = mapper;
    }

    @GetMapping("{name}")
    public String getMessageFromIntegrationService(@PathVariable("name") String name) {
        return integrationGateway.sendMessage(name);
    }

    @PostMapping
    public String processProductDetails(@RequestBody ProductRequest productRequest) {
        return integrationGateway.processProductGateway(mapper.map(productRequest, Product.class));
    }

}
