package car.sharing.demo;

import car.sharing.demo.config.BotInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = TestConfig.class)
class DemoApplicationTests {
    @MockBean
    private BotInitializer botInitializer;

    @Test
    void contextLoads() {
    }
}
