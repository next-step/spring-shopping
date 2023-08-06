package shopping.accept;


import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import shopping.TestDefaultLocation;
import shopping.mart.service.ProductService;
import shopping.mart.service.dto.ProductResponse;

@ContextConfiguration(classes = TestDefaultLocation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
abstract class AcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected List<ProductResponse> findAllProducts() {
        return productService.findAllProducts();
    }
}
