package shopping.integration;

import static org.hamcrest.Matchers.containsString;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import shopping.domain.Product;
import shopping.repository.ProductRepository;

class ProductIntegrationTest extends IntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 목록 페이지를 조회한다.")
    @Test
    void getAllProduct() {
        // given
        Product chicken = new Product("치킨", "/chicken.jpg", 12300L);

        productRepository.save(chicken);

        // when, then
        RestAssured
                .given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(
                        containsString(chicken.getName()),
                        containsString(chicken.getImageUrl())
                );
    }
}
