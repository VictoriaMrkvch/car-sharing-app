package car.sharing.demo.service.payment;

import car.sharing.demo.config.StripeConfig;
import car.sharing.demo.dto.payment.CreatePaymentRequestDto;
import car.sharing.demo.dto.payment.PaymentDto;
import car.sharing.demo.mapper.payment.PaymentMapper;
import car.sharing.demo.model.Payment;
import car.sharing.demo.model.Rental;
import car.sharing.demo.model.Status;
import car.sharing.demo.repository.payment.PaymentRepository;
import car.sharing.demo.service.rental.RentalService;
import car.sharing.demo.service.telegram.NotificationService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private static final String PAYMENT_URL = "http://localhost:8082/payments";
    private static final BigDecimal FINE_MULTIPLIER = new BigDecimal(100);
    private final StripeConfig stripeConfig;
    private final PaymentRepository paymentRepository;
    private final RentalService rentalService;
    private final PaymentMapper paymentMapper;
    private final NotificationService notificationService;

    @Transactional
    @Override
    public PaymentDto createPaymentSession(CreatePaymentRequestDto requestDto)
            throws StripeException, MalformedURLException {
        Stripe.apiKey = stripeConfig.getKey();

        long amount = calculatePaymentAmount(requestDto.getRentalId())
                .multiply(BigDecimal.valueOf(100))
                .longValue();

        SessionCreateParams.Builder builder = new SessionCreateParams.Builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(PAYMENT_URL + "/success" + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(PAYMENT_URL + "/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amount)
                                                .setProductData(
                                                        SessionCreateParams.LineItem
                                                                .PriceData.ProductData.builder()
                                                                .setName("Car Rental")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                );

        Session session = Session.create(builder.build());
        Payment payment = getPayment(requestDto, session);
        payment.setRental(rentalService.getRentalById(requestDto.getRentalId()));
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public String handleSuccessfulPayment(String sessionId) throws MalformedURLException {
        Payment payment = paymentRepository.findBySessionId(sessionId);

        if (payment != null) {
            payment.setStatus(Status.PAID);
            payment.setUrl(new URL(PAYMENT_URL + "/success"));
            paymentRepository.save(payment);
            notificationService.sendNotification("Payment successful for payment ID: "
                    + payment.getId());
            return "Your payment was successful!";
        } else {
            return "Payment was not successful.";
        }
    }

    @Override
    public String handleCancelledPayment() {
        return "Payment can be made later. The session is available for 23 hours.";
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public BigDecimal calculatePaymentAmount(Long rentalId) {
        Rental rental = rentalService.getRentalById(rentalId);
        long days = ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getReturnDate());
        BigDecimal amount = BigDecimal.valueOf(days).multiply(rental.getCar().getDailyFee());
        if (rental.getActualReturnDate().equals(rental.getReturnDate())) {
            return amount;
        } else {
            long fineDays = ChronoUnit.DAYS.between(rental.getReturnDate(),
                    rental.getActualReturnDate());
            BigDecimal fineAmount = BigDecimal.valueOf(fineDays).multiply(FINE_MULTIPLIER);
            return amount.add(fineAmount);
        }
    }

    private Payment getPayment(CreatePaymentRequestDto requestDto, Session session)
            throws MalformedURLException {
        String sessionId = session.getId();
        String sessionUrl = session.getCancelUrl();

        BigDecimal amountToPay = new BigDecimal(session.getAmountTotal());
        Payment payment = new Payment();
        payment.setStatus(Status.PENDING);
        payment.setType(requestDto.getType());
        payment.setUrl(new URL(sessionUrl));
        payment.setSessionId(sessionId);
        payment.setAmount(amountToPay.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
        return payment;
    }
}
