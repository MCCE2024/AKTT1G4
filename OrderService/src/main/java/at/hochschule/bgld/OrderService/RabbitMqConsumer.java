package at.hochschule.bgld.OrderService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqConsumer {
    @Autowired
    private RabbitMqProducer rabbitMqProducer;
    ObjectMapper objectMapper = new ObjectMapper();

    public void consumeMessage(final String content) {
        try {
            final MessageDto message = objectMapper.readValue(content, MessageDto.class);
            if(message.getSource().equals("PAYMENT")){
                System.out.println("ORDER: Message consumed: '" + message + "'");
                this.rabbitMqProducer.produceMessage(message);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
