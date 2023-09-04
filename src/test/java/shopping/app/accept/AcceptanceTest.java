package shopping.app.accept;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import shopping.TestDefaultLocation;
import shopping.mart.dto.ProductResponse;
import shopping.mart.service.ProductService;

import java.util.List;

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
        return productService.findAllProducts(PageRequest.of(
                0, 10, Sort.by(Direction.DESC, "createdAt")));
    }
}
