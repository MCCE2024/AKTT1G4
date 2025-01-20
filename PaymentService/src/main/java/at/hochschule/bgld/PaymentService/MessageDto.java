package at.hochschule.bgld.PaymentService;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageDto {
    private String orderNumber;
    private String source;
}
