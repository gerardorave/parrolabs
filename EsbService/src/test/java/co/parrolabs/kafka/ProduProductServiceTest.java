package co.parrolabs.kafka;

import co.parrolabs.kafka.config.ProducerProductKafkaConfig;
import co.parrolabs.dto.ProductDto;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9093", "port=9093"})
@TestPropertySource(properties = {"spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer"})
@SpringBootTest
public class ProduProductServiceTest {

    @Autowired
    public ProducerProductKafkaConfig config;

    private String bootstrapServers="localhost:9093";

    private static final String KAFKA_TOPIC = "vpn.kafka.product.create";


    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    @DisplayName("Creates a product")
    void createOneProduct() {

        ProducerProductKafka producer = new ProducerProductKafka(configureProducer());

        ProductDto product = new ProductDto().builder().id(UUID.randomUUID()).name("Kafka Product 1").build();
        producer.sendProductMessage(product);
        List<ConsumerRecord<String, ProductDto>> buffer = new ArrayList<>();
        try {
            Consumer<String, ProductDto> consumer = createConsumer().createConsumer();
            consumer.subscribe(Collections.singletonList(KAFKA_TOPIC));
            buffer = getBufferConsumer(consumer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(buffer.size() > 0);

    }

    private List<ConsumerRecord<String, ProductDto>> getBufferConsumer(Consumer<String, ProductDto> consumer) {

        List<ConsumerRecord<String, ProductDto>> buffer = new ArrayList<>();
        try {
            ConsumerRecords<String, ProductDto> records = consumer.poll(0);
            while(records != null && buffer.size() == 0 ) {
                for (ConsumerRecord<String, ProductDto> record : records.records(KAFKA_TOPIC)) {
                    buffer.add(record);
                }
                records = consumer.poll(0);
            }
        }

        finally {
            consumer.commitSync();
            consumer.close();
            return buffer;
        }

    }

    private ConsumerFactory<String, ProductDto> createConsumer() throws Exception{
        Map<String, String> props= new HashMap<>();
        props.remove(ConsumerConfig.CLIENT_ID_CONFIG);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ti-kafka-product-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        embeddedKafkaBroker.brokerProperties(props);
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("ti-kafka-product-group", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        JsonDeserializer jsonDeserializer = new JsonDeserializer<>(ProductDto.class, false);
        return new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), jsonDeserializer);
    }

    private KafkaTemplate<String, ProductDto> configureProducer() {
        Map<String, Object> propsFinal = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        propsFinal.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        propsFinal.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        propsFinal.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(propsFinal));
    }
}
