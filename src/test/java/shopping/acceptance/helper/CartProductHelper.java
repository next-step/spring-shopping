package shopping.acceptance.helper;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import shopping.dto.request.CartProductCreateRequest;

public class CartProductHelper {

    private CartProductHelper() {
        throw new UnsupportedOperationException();
    }

    public static ExtractableResponse<Response> createCartProduct(
        final String jwt,
        final Long productId
    ) {
        return RestHelper.post("/api/cart-product", jwt, new CartProductCreateRequest(productId));
    }
}
