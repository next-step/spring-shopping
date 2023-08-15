package integration.helper;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import shopping.cart.dto.request.CartItemCreationRequest;
import shopping.cart.dto.response.AllCartItemsResponse;

import static integration.helper.CommonRestAssuredUtils.*;

public class CartHelper {

    public static void addCartItem(Long productId) {
        ExtractableResponse<Response> response = post("/cart", new CartItemCreationRequest(productId),
            LONG_EXPIRED_TOKEN);
    }

    public static AllCartItemsResponse getAllCartItems() {
        return get("/cart", LONG_EXPIRED_TOKEN).body().as(AllCartItemsResponse.class);
    }
}
