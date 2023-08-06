package shopping.integration.util;

import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.CartCreateRequest;

public class CartUtil {

    public static Long createCartItem(String accessToken, long productId) {
        String location = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartCreateRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");

        return Long.parseLong(location.split("/")[2]);
    }
}
