package co.parrolabs.service.impl;

import co.parrolabs.dto.AdditionalInfoForOrderResponse;
import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;
import co.parrolabs.dto.request.ProductRequest;
import co.parrolabs.httpclient.feign.ClientFeignServiceModelsProduct;

import co.parrolabs.kafka.ProducerProductKafka;
import co.parrolabs.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {


    private ClientFeignServiceModelsProduct clientFeignServiceModelsProduct;

    private ProducerProductKafka producerProductKafka;

    @Autowired
    public ProductServiceImpl(ClientFeignServiceModelsProduct clientFeignServiceModelsProduct, ProducerProductKafka producerProductKafka) {

        this.clientFeignServiceModelsProduct = clientFeignServiceModelsProduct;
        this.producerProductKafka = producerProductKafka;
    }

    @Override
    @Cacheable(value = "productCache", key = "#id", unless = "#result == null")
    public Optional<ProductDto> getProductById(UUID id) {
        return Optional.of(clientFeignServiceModelsProduct.getProductById(id));
    }

    @Override
    public Optional<ProductDto> saveProduct(ProductRequest productRequest) {

        if (productRequest.getId() == null || "".equals(productRequest.getId())) {
            productRequest.setId(UUID.randomUUID());
        }
        return Optional.of(clientFeignServiceModelsProduct.saveProduct(productRequest));
    }

    @Override
    @Cacheable(value = "productCache")
    public Stream<List<ProductMongoDbDto>> getDeletedProducts() {

        return Stream.of(clientFeignServiceModelsProduct.getDeletedProducts());
        //mapper.map(products, new TypeToken<List<ProductMongoDbDto>>() {
        //}.getType());
    }

    @Override
    public Optional<ProductDto> updateProduct(ProductRequest productRequest) {
        if (productRequest.getId() == null || "".equals(productRequest.getId())) {
            productRequest.setId(UUID.randomUUID());
        }
        return Optional.of(clientFeignServiceModelsProduct.saveProduct(productRequest));
    }

    @Override
    public Optional<ProductMongoDbDto> deleteProduct(UUID id) {
        return Optional.of(clientFeignServiceModelsProduct.deleteProduct(id));
    }
    @Override
    public Optional<AdditionalInfoForOrderResponse> getAdditionalInfoForOrder(AdditionalInfoForOrderRequest additionalInfoForOrderRequest){
        return Optional.of(clientFeignServiceModelsProduct.getAdditionalInfoForOrder(additionalInfoForOrderRequest));
    }

    @Override
    public void saveKafkaProducts(List<ProductDto> productDtos){
        for(ProductDto productDto: productDtos) {
            producerProductKafka.sendProductMessage(productDto);
        }
    }

}
