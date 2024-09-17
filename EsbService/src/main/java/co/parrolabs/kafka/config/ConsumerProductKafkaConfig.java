package co.parrolabs.kafka.config;

import co.parrolabs.dto.ProductDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@EnableKafka
public class ConsumerProductKafkaConfig {

    private static final String CONSUMER_GROUP_ID = "vpn-kafka-product-group";

    private static final String AUTO_OFFSET_RESET_CONFIG = "earliest";

    @Value("${vpn.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${vpn.kafka.product.batchConcurrency}")
    private int kafkaListenerConcurrency;

    @Bean("productContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ProductDto> productContainerFactory(final KafkaProperties kafkaProperties) {

        ConcurrentKafkaListenerContainerFactory<String, ProductDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        //factory.setConcurrency(kafkaListenerConcurrency);
        //factory.setBatchListener(true);
        factory.setConsumerFactory(consumerKafkaFactory(kafkaProperties));
        return factory;
    }

    private ConsumerFactory<String, ProductDto> consumerKafkaFactory(final KafkaProperties kafkaProperties) {

        JsonDeserializer jsonDeserializer = new JsonDeserializer<>(ProductDto.class, false);

        return new DefaultKafkaConsumerFactory<>(buildKafkaConsumerProperties(kafkaProperties),
                new StringDeserializer(), jsonDeserializer);
    }

    private Map<String, Object> buildKafkaConsumerProperties(final KafkaProperties properties) {

        final Map<String, Object> props = properties.buildConsumerProperties();
        //Ensure consumer generates their owm client ID
        props.remove(ConsumerConfig.CLIENT_ID_CONFIG);

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG);
        return props;
    }
}
