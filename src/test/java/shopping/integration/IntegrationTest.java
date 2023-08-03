package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import shopping.repository.CartItemRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    CartItemRepository cartItemRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        cartItemRepository.deleteAll();
    }
}
