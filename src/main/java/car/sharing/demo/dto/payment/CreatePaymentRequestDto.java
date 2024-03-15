package car.sharing.demo.dto.payment;

import car.sharing.demo.model.PaymentType;
import lombok.Data;

@Data
public class CreatePaymentRequestDto {
    private Long rentalId;
    private PaymentType type;
}
