package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.integration.config.IntegrationTest;
import shopping.integration.util.AuthUtil;
import shopping.integration.util.CartUtil;

@IntegrationTest
class OrderIntegrationTest {

    private static final String ORDER_API_URL = "/api/orders";

    @Test
    @DisplayName("주문에 성공한다")
    void createOrder() {
        // given
        final String accessToken = AuthUtil.accessToken();

        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 2L);

        // when & then
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(ORDER_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", ORDER_API_URL + "/1");
    }
}
