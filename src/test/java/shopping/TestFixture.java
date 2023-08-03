package shopping;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.domain.entity.ProductEntity;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.LoginRequest;

public class TestFixture {

    private static Long sequence = 1L;

    public static ProductEntity createProductEntity(String name, int price) {
        return new ProductEntity(sequence, name, "uuid" + sequence++, price);
    }

    public static ExtractableResponse<Response> login(final LoginRequest loginRequest) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(loginRequest)
            .when().post("/user/login/token")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> addCartItem(String accessToken,
        CartItemAddRequest cartItemAddRequest) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(cartItemAddRequest)
            .when().post("/cart/items")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> readCartItems(String accessToken) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .when().get("/cart/items")
            .then().log().all()
            .extract();
    }
}
