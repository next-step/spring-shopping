package shopping.acceptance.helper;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import shopping.dto.request.CartProductRequest;

public class CartProductHelper {

    private CartProductHelper() {
        throw new UnsupportedOperationException();
    }

    public static ExtractableResponse<Response> createCartProduct(
        final String jwt,
        final CartProductRequest request
    ) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .body(request)
            .when().post("/api/cart")
            .then().log().all()
            .extract();
    }
}
