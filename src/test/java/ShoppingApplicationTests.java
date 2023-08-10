import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import shopping.ShoppingApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = ShoppingApplication.class)
class ShoppingApplicationTests {

    @Test
    void contextLoads() {
    }

}
