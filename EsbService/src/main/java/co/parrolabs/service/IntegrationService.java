package co.parrolabs.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public interface IntegrationService {

    Message<String> receiveMessage(Message<String> message) throws MessagingException;
    void anotherMessage(Message<String> message) throws MessagingException;
}

