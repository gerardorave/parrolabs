package co.parrolabs.controller;

import co.parrolabs.dto.AdditionalInfoForOrderResponse;
import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;
import co.parrolabs.dto.request.ProductRequest;
import co.parrolabs.service.OrderService;
import co.parrolabs.service.ProductIntegrationService;
import co.parrolabs.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    public Mono<ProductDto> getProductById(@PathVariable("id") UUID idProduct) {
        return Mono.justOrEmpty(productService.getProductById(idProduct).get());
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductDto> saveProduct(@RequestBody ProductRequest productRequest) {

        return Mono.justOrEmpty(productService.saveProduct(productRequest));
    }

    @GetMapping(value = "/deletedProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<List<ProductMongoDbDto>> getDeletedProducts() {
        return Flux.fromStream(productService.getDeletedProducts());
    }


    @DeleteMapping(value = "/deleteProduct/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductMongoDbDto> deleteProduct(@PathVariable("id") UUID id)  {
        return Mono.justOrEmpty(productService.deleteProduct(id));
    }

    @PutMapping(value = "/updateProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductDto> updateProduct(@RequestBody ProductRequest productRequest) {
        return Mono.justOrEmpty(productService.updateProduct(productRequest));
    }

    @PostMapping(value = "/additionalInfoForOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AdditionalInfoForOrderResponse> getAdditionalInfoForOrder(@RequestBody AdditionalInfoForOrderRequest additionalInfoForOrderRequest) {
        return Mono.justOrEmpty(productService.getAdditionalInfoForOrder(additionalInfoForOrderRequest));
    }

    @PostMapping(value = "/saveKafkaProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveKafkaProducts(@RequestBody List<ProductDto> productDtos) {

        productService.saveKafkaProducts(productDtos);
        return new ResponseEntity<>("Products sent to the queue.", HttpStatus.OK);
    }

}
