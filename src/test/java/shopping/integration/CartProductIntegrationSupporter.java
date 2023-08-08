package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.response.LoginResponse;
import shopping.dto.request.UpdateCartProductRequest;

public class CartProductIntegrationSupporter {

    static ExtractableResponse<Response> addProduct(Long productId) {
        String accessToken = getAccessToken();

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart/products/{productId}", productId)
                .then().log().all().extract();
    }

    static ExtractableResponse<Response> findCartProducts() {
        String accessToken = getAccessToken();

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/products")
                .then().log().all().extract();
    }

    static ExtractableResponse<Response> deleteCartProduct(Long id) {
        String accessToken = getAccessToken();

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/{id}", id)
                .then().log().all().extract();
    }

    static ExtractableResponse<Response> updateCartProduct(Long id, UpdateCartProductRequest request) {
        String accessToken = getAccessToken();

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch("/cart/{id}", id)
                .then().log().all().extract();
    }

    static ExtractableResponse<Response> updateProductWithJson(Long id, String jsonRequest) {
        String accessToken = getAccessToken();

        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(jsonRequest)
            .when().patch("/cart/{id}", id)
            .then().log().all().extract();
    }

    private static String getAccessToken() {
        String email = "woowa1@woowa.com";
        String password = "1234";

        return AuthIntegrationSupporter
            .login(email, password)
            .as(LoginResponse.class)
            .getAccessToken();
    }

}