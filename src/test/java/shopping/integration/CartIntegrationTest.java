package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.CartRequest;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;
import shopping.integration.config.IntegrationTest;

@IntegrationTest
public class CartIntegrationTest {
    @Test
    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    void addCart() {
        // given
        String accessToken = RestAssured
                .given().log().all()
                .body(new LoginRequest("test@gmail.com", "test1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all().extract().as(LoginResponse.class).getAccessToken();

        // when, then
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartRequest(1L))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
