package car.sharing.demo.dto.payment;

import java.math.BigDecimal;
import java.net.URL;
import lombok.Data;

@Data
public class PaymentDto {
    private Long id;
    private String status;
    private String type;
    private Long rentalId;
    private URL url;
    private String sessionId;
    private BigDecimal amount;
}
