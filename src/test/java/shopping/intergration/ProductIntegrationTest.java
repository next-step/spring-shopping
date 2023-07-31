package shopping.intergration;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class ProductIntegrationTest {

    @Test
    @DisplayName("상품 목록을 조회한다.")
    void readProducts() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().get("/")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.TEXT_HTML_VALUE)
                .body(both(containsString("Pizza"))
                        .and(containsString("13000"))
                        .and(containsString("Chicken"))
                        .and(containsString("10000"))
                        .and(containsString("Salad"))
                        .and(containsString("20000")));
    }
}
