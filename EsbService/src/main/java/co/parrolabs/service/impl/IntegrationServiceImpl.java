package co.parrolabs.service.impl;

import co.parrolabs.service.IntegrationService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class IntegrationServiceImpl implements IntegrationService {

    @Override
    @ServiceActivator(inputChannel = "integration.gateway.channel", outputChannel = "integration.gateway.channel.service")
    public Message<String> receiveMessage(Message<String> message) throws MessagingException {
        MessageBuilder.fromMessage(message);
        Message<String> newMessage = MessageBuilder.withPayload(
                message.getPayload()+" modified in integration gateway channel, ").build();
        return newMessage;

    }

    @Override
    @ServiceActivator(inputChannel = "integration.gateway.channel.service.activator")
    public void anotherMessage(Message<String> message) throws MessagingException {
         MessageChannel replyChannel = (MessageChannel)
         message.getHeaders().getReplyChannel();
        Message<String> newMessage = MessageBuilder.withPayload(
                message.getPayload()+" and received into integration gateway channel service activator").build();
        replyChannel.send(newMessage);


    }
}
