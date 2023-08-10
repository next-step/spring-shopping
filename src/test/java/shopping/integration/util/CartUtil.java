package shopping.integration.util;

import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.CartCreateRequest;

public class CartUtil {

    public static final String CART_API_URL = "/api/carts";

    public static void createCartItem(String accessToken, long productId) {
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartCreateRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(CART_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }
}
