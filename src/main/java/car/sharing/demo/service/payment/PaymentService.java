package car.sharing.demo.service.payment;

import car.sharing.demo.dto.payment.CreatePaymentRequestDto;
import car.sharing.demo.dto.payment.PaymentDto;
import com.stripe.exception.StripeException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

public interface PaymentService {
    PaymentDto createPaymentSession(CreatePaymentRequestDto requestDto)
            throws StripeException, MalformedURLException;

    String handleSuccessfulPayment(String sessionId) throws MalformedURLException;

    String handleCancelledPayment();

    List<PaymentDto> getAllPayments();

    BigDecimal calculatePaymentAmount(Long rentalId);
}
