package co.parrolabs.service.impl;

import co.parrolabs.model.Product;
import co.parrolabs.service.ProductIntegrationService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductntegrationServiceImpl implements ProductIntegrationService {

    @Override
    @ServiceActivator(inputChannel = "integration.product.objectToJson.channel", outputChannel = "integration.product.jsonToObject.fromTransformer.channel")
    public Message<?> receiveMessage(Message<?> message) throws MessagingException {
        System.out.println("##########################");
        System.out.println(message);
        System.out.println("##########################");
        System.out.println("Object To Json:"+ message.getPayload());
        return message;

    }

    @Override
    @ServiceActivator(inputChannel = "integration.product.jsonToObject.fromTransformer.channel")
    public void processJsonToObject(Message<?> message) throws MessagingException {
         MessageChannel replyChannel = (MessageChannel)
         message.getHeaders().getReplyChannel();
         MessageBuilder.fromMessage(message);
        System.out.println("##########################");
        System.out.println("Json To  Object :"+ message.getPayload());
        Product product = (Product)message.getPayload();
        Message<?> newMessage =  MessageBuilder.withPayload(product.toString()).build();
               replyChannel.send(newMessage);


    }
}
