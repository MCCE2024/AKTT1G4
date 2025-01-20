package at.hochschule.bgld.OrderService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceMessage(final MessageDto messageDto) {
        messageDto.setSource("ORDER");

        ObjectMapper om = new ObjectMapper();
        try {
            String content = om.writeValueAsString(messageDto);

            System.out.println("ORDER: Sending message with content: '" + content + "'");

            this.rabbitTemplate.convertAndSend(RabbitMqConfig.ORDER_QUEUE_NAME, content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
