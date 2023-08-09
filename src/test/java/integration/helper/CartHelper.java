package integration.helper;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import shopping.cart.dto.request.CartItemCreationRequest;

public class CartHelper {

    private CartHelper() {
    }

    public static ExtractableResponse<Response> createCartItem(
        CartItemCreationRequest cartItemCreationRequest
    ) {
        return CommonRestAssuredUtils.post(
            "/cart",
            cartItemCreationRequest,
            CommonRestAssuredUtils.DEFAULT_TOKEN
        );
    }

}
