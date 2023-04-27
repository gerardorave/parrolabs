package co.parrolabs.service;

import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.ProductRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDto getProductById(UUID id);

    ProductDto saveProduct(ProductRequest productRequest);

    List<ProductMongoDbDto> getDeletedProducts();

    ProductMongoDbDto deleteProduct(UUID id);

    ProductDto updateProduct(ProductRequest productRequest);

}
