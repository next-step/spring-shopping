package shopping.integration;

import static org.hamcrest.Matchers.containsString;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.ProductRequest;

class ProductIntegrationTest extends IntegrationTest {

    @DisplayName("상품 목록 페이지를 조회한다.")
    @Test
    void getAllProduct() {
        // given
        ProductRequest chicken = new ProductRequest("치킨", "/chicken.jpg", 12300L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(chicken)
                .when().post("/products")
                .then().log().all();

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
