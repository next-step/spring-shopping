package shopping.acceptance.helper;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import shopping.dto.request.CartProductRequest;

public class CartProductHelper {

    private CartProductHelper() {
        throw new UnsupportedOperationException();
    }

    public static ExtractableResponse<Response> createCartProduct(
        final String jwt,
        final CartProductRequest request
    ) {
        return RestHelper.post("/api/cartProduct", jwt, request);
    }
}
