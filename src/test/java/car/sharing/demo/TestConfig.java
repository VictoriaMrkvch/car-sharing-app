package car.sharing.demo;

import car.sharing.demo.config.BotInitializer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@Import({BotInitializer.class})
public class TestConfig {
}
