package co.parrolabs.kafka;

import co.parrolabs.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Component
@Slf4j
public class ProducerProductKafka {

    private static final String KAFKA_TOPIC = "vpn.kafka.product.create";

    private final KafkaTemplate<String, ProductDto> kafkaTemplate;

    @Autowired
    public ProducerProductKafka(KafkaTemplate<String, ProductDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductMessage(final ProductDto productDto) {

        final String messageId = UUID.randomUUID().toString();
        final String productId = String.valueOf(productDto.getId());

        log.info("Attempting to send Product message - messageId: {} productId:{}", messageId, productId);

        final ListenableFuture<SendResult<String, ProductDto>> future =
                kafkaTemplate.send(KAFKA_TOPIC, messageId, productDto);

        future.addCallback(new ListenableFutureCallback<SendResult<String, ProductDto>>() {
            @Override
            public void onSuccess(SendResult<String, ProductDto> result) {
                ProducerRecord<String, ProductDto> producerRecord = result.getProducerRecord();
                final String messageId = producerRecord.key();
                final String productId = String.valueOf(productDto.getId());
                log.info("Successfully sent ParcelCharges message - messageId: {} productId:{}", messageId, productId);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to process ParcelCharges message: {}", ex.getMessage(), ex);
            }
        });
    }
}
