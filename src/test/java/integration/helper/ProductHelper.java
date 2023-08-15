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

    public static Long createProductAndGetId(ProductCreationRequest productCreationRequest) {
        ExtractableResponse<Response> response = CommonRestAssuredUtils.post(
            "/products",
            productCreationRequest,
            CommonRestAssuredUtils.LONG_EXPIRED_TOKEN
        );
        Integer id = response.jsonPath().get("id");
        return id.longValue();
    }

    public static ProductCreationRequest createProductCreationRequest() {
        return new ProductCreationRequest("피자", "10000", "imageUrl");
    }

    public static ProductCreationRequest createProductCreationRequest2() {
        return new ProductCreationRequest("치킨", "20000", "imageUrl2");
    }

}
