package car.sharing.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Data
public class BotConfig {
    @Value("${bot.username}")
    private String botUserName;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.chatId}")
    private String chatId;
}
