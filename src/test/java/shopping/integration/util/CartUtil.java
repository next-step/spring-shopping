package shopping.integration.util;

import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.CartRequest;

public class CartUtil {

    public static void createCartItem(String accessToken, long productId) {
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }
}
