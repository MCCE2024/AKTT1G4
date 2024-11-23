package at.hochschule.bgld.OrderService;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageDto {
    private String orderNumber;
    private String source;
}
