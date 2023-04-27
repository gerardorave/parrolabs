package co.parrolabs.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public interface ProductIntegrationService {
    Message<?> receiveMessage(Message<?> message) throws MessagingException;
    void processJsonToObject(Message<?> message) throws MessagingException;
}
