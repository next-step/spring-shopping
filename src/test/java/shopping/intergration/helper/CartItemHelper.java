package shopping.intergration.helper;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;

public class CartItemHelper {
    public static ExtractableResponse<Response> addCartItemRequest(final String accessToken, final Long productId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartItemAddRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart-items")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> addCartItemRequestWithoutAccessToken(final Long productId) {
        return RestAssured
                .given().log().all()
                .body(new CartItemAddRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart-items")
                .then().log().all().extract();
    }

    public static CartItemResponse addCartItem(final String accessToken, final Long productId) {
        return RestAssuredHelper.extractObject(RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartItemAddRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart-items")
                .then().log().all().extract(), CartItemResponse.class);
    }

    public static ExtractableResponse<Response> getCartItemsRequest(final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart-items")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> updateCartItemRequest(final String accessToken, final Long targetCartItemId, final int updateQuantity) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartItemUpdateRequest(updateQuantity))
                .when().patch("/cart-items/{cartItemId}", targetCartItemId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> deleteCartItemRequest(final String accessToken, final Long targetCartItemId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart-items/{cartItemId}", targetCartItemId)
                .then().log().all().extract();
    }
}
