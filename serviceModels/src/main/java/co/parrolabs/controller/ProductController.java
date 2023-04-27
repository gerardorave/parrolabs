package co.parrolabs.controller;

import co.parrolabs.dto.AdditionalInfoForOrderResponse;
import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;
import co.parrolabs.dto.request.ProductRequest;
import co.parrolabs.service.OrderService;
import co.parrolabs.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    private OrderService orderService;

    @Autowired
    public ProductController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") UUID idProduct) {
        ProductDto productDto = productService.getProductById(idProduct);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> saveProduct(@RequestBody @Valid ProductRequest productRequest) {
        ProductDto productDtoResponse = productService.saveProduct(productRequest);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/deletedProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductMongoDbDto>> getDeletedProducts() {
        List<ProductMongoDbDto> deletedProductsResponse = productService.getDeletedProducts();
        return new ResponseEntity<>(deletedProductsResponse, HttpStatus.OK);
    }


    @DeleteMapping(value = "/deleteProduct/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductMongoDbDto> deleteProduct(@PathVariable("id") UUID id)  {
        ProductMongoDbDto productMongoDbDto = productService.deleteProduct(id);
        return new ResponseEntity<>(productMongoDbDto, HttpStatus.OK);
    }

    @PutMapping(value = "/updateProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductRequest productRequest) {
        ProductDto productDtoResponse = productService.updateProduct(productRequest);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/additionalInfoForOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdditionalInfoForOrderResponse> getAdditionalInfoForOrder(@RequestBody @Valid AdditionalInfoForOrderRequest additionalInfoForOrderRequest) {
        AdditionalInfoForOrderResponse additionalInfoForOrderResponse = orderService.getAdditionalInfoForOrder(additionalInfoForOrderRequest);
        return new ResponseEntity<>(additionalInfoForOrderResponse, HttpStatus.OK);
    }

}
