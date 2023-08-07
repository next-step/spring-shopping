package integration.helper;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import shopping.product.dto.request.ProductCreationRequest;

public class ProductHelper {

    private ProductHelper() {
    }

    public static ExtractableResponse<Response> createProduct(
        ProductCreationRequest productCreationRequest) {
        ExtractableResponse<Response> response = CommonRestAssuredUtils.post(
            "/products",
            productCreationRequest,
            CommonRestAssuredUtils.LONG_EXPIRED_TOKEN
        );
        return response;
    }

}
