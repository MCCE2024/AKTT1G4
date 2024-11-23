package at.hochschule.bgld.PaymentService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class RabbitMqProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5000L)
    public void produceMessage() {
        final MessageDto messageDto = new MessageDto();
        messageDto.setSource("PAYMENT");
        messageDto.setOrderNumber("1");

        ObjectMapper om = new ObjectMapper();
        String content = null;
        try {
            content = om.writeValueAsString(messageDto);

            System.out.println("PAYMENT: Sending message with content: '" + content + "'");

            this.rabbitTemplate.convertAndSend(RabbitMqConfig.ORDER_QUEUE_NAME, content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
