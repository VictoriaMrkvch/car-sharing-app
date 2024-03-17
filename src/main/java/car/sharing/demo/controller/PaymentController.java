package car.sharing.demo.controller;

import car.sharing.demo.dto.payment.CreatePaymentRequestDto;
import car.sharing.demo.dto.payment.PaymentDto;
import car.sharing.demo.service.payment.PaymentService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.MalformedURLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment management", description = "Endpoints for managing payments")
@RequiredArgsConstructor
@RequestMapping("/payments")
@RestController
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "Create payment", description = "Create payment stripe session")
    @PostMapping
    public PaymentDto createPaymentSession(@RequestBody CreatePaymentRequestDto requestDto)
            throws StripeException, MalformedURLException {
        return paymentService.createPaymentSession(requestDto);
    }

    @Operation(summary = "Success payment", description = "Endpoint for success payment")
    @GetMapping("/success")
    public String successEndpoint(@RequestParam String sessionId) throws MalformedURLException {
        return paymentService.handleSuccessfulPayment(sessionId);
    }

    @Operation(summary = "Cancel payment", description = "Endpoint for cancel payment")

    @GetMapping("/cancel")
    public String cancelEndpoint() {
        return paymentService.handleCancelledPayment();
    }

    @Operation(summary = "Get payments", description = "Get list of all payments")
    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public List<PaymentDto> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
