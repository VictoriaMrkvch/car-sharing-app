package car.sharing.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class StripeConfig {
    @Value("${stripe.secret.key}")
    private String key;
}
