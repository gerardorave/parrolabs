package co.parrolabs.service;

import co.parrolabs.dto.AdditionalInfoForOrderResponse;
import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;
import co.parrolabs.dto.request.ProductRequest;
import co.parrolabs.model.Product;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface ProductService {

    Optional<ProductDto> getProductById(UUID id);

    Optional<ProductDto> saveProduct(ProductRequest productRequest);

    Stream<List<ProductMongoDbDto>> getDeletedProducts();

    Optional<ProductMongoDbDto> deleteProduct(UUID id);

    Optional<ProductDto> updateProduct(ProductRequest productRequest);

    Optional<AdditionalInfoForOrderResponse> getAdditionalInfoForOrder(AdditionalInfoForOrderRequest additionalInfoForOrderRequest);

    void saveKafkaProducts(List<ProductDto> productDtos);

}
