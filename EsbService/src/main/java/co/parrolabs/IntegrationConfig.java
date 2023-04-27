package co.parrolabs;

import co.parrolabs.dto.ProductDto;
import co.parrolabs.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.transformer.MessageTransformingHandler;
import org.springframework.integration.transformer.MethodInvokingTransformer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
@IntegrationComponentScan
@PropertySource({"classpath:application.yml"})
public class IntegrationConfig {


    @Bean
    public ModelMapper modelMapper() { return new ModelMapper();}

    @Bean
    public MessageChannel receiverChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel replyChannel() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "integration.product.gateway.channel", outputChannel = "integration.product.objectToJson")
    public ObjectToJsonTransformer objectToJson() {
        return new ObjectToJsonTransformer(getMapper());
    }

    @Bean
    public Jackson2JsonObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return new Jackson2JsonObjectMapper(mapper);
    }

    @Bean
    @Transformer(inputChannel = "integration.product.objectToJson", outputChannel = "integration.product.jsonToObject")
    public JsonToObjectTransformer jsonToObject()
    {
        return new JsonToObjectTransformer(Product.class);

    }

}
