package at.hochschule.bgld.PaymentService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqConsumer {
    ObjectMapper objectMapper = new ObjectMapper();

    public void consumeMessage(final String content) {
        try {
            final MessageDto message = objectMapper.readValue(content, MessageDto.class);
            if(message.getSource().equals("ORDER")){
                System.out.println("PAYMENT: Message consumed: '" + message + "'");
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
