package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import shopping.domain.cart.Product;
import shopping.repository.ProductRepository;

import static org.hamcrest.Matchers.containsString;

@DisplayName("상품 인수 테스트")
class ProductIntegrationTest extends IntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 목록 페이지를 조회 성공시 200 Ok")
    @Test
    void getAllProduct() {
        // given
        Product chicken = new Product("치킨", "/chicken.jpg", 12300.0);

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
