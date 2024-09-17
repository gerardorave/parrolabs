package co.parrolabs.kafka;

//import co.parrolabs.client.httpclient.feign.ClientFeignServiceModelsProduct;
import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.request.ProductRequest;

import co.parrolabs.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerProductKafka {

    private static final String KAFKA_TOPIC = "vpn.kafka.product.create";

    private ProductService productService;

    private ModelMapper mapper;

    @Autowired
    public ConsumerProductKafka(ProductService productService, ModelMapper mapper){
        this.productService = productService;
        this.mapper = mapper;
    }

    @KafkaListener(containerFactory = "productContainerFactory", topics = KAFKA_TOPIC)
    public void consumeMessage(final ConsumerRecord<String, ProductDto> consumerRecord) {
        final String messageId = consumerRecord.key();
        final ProductDto productDto = consumerRecord.value();
        productService.saveProduct(mapper.map(productDto, ProductRequest.class));
        log.info("Received productContainerFactoryMessage - messageId: {} isOnRoute: {}", messageId, (productDto != null));
    }


}