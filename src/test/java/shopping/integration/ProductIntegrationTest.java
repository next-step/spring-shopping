package shopping.integration;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import shopping.integration.config.IntegrationTest;

@IntegrationTest
public class ProductIntegrationTest {
    @Test
    @DisplayName("메인에서 상품 목록을 조회할 수 있다.")
    void findAllProducts() {
        RestAssured.given().log().all()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .when().get("/")
                .then()
                .statusCode(200)
                .body(Matchers.contains("치킨", "20000", "피자", "25000"));
    }
}
