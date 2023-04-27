package co.parrolabs.kafka;

import co.parrolabs.dto.ProductDto;
import co.parrolabs.model.Product;
import co.parrolabs.repository.jpa.ProductRepository;
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

    private ProductRepository productRepository;

    private ModelMapper mapper;

    @Autowired
    public ConsumerProductKafka(ProductRepository productRepository, ModelMapper mapper){
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @KafkaListener(containerFactory = "productContainerFactory", topics = KAFKA_TOPIC)
    public void consumeMessage(final ConsumerRecord<String, ProductDto> consumerRecord) {
        final String messageId = consumerRecord.key();
        final ProductDto productDto = consumerRecord.value();
        productRepository.save(mapper.map(productDto, Product.class));
        log.info("Received productContainerFactoryMessage - messageId: {} isOnRoute: {}", messageId, (productDto != null));
    }


}