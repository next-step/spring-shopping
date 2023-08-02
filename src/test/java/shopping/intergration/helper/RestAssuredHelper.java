package shopping.intergration.helper;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.LoginResponse;

public class RestAssuredHelper {

    private static final String JSON_ROOT_PATH = "";

    public static LoginResponse login(final String email, final String password) {
        return extractObject(RestAssured
                .given().log().all()
                .body(new LoginRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract(), LoginResponse.class);
    }

    public static CartItemResponse addCartItem(final String accessToken, final Long productId) {
        return extractObject(RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartItemAddRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart-items")
                .then().log().all().extract(), CartItemResponse.class);
    }

    public static <T> T extractObject(final ExtractableResponse<Response> response, Class<T> objectType) {
        return response.jsonPath().getObject(JSON_ROOT_PATH, objectType);
    }
}
