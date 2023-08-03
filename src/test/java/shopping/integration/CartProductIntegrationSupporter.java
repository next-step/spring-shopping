package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.LoginResponse;

public class CartProductIntegrationSupporter {

    static ExtractableResponse<Response> addProduct(Long memberId, Long productId) {
        String email = "woowa1@woowa.com";
        String password = "1234";

        String accessToken = AuthIntegrationSupporter
                .login(email, password)
                .as(LoginResponse.class)
                .getAccessToken();

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart/products/{productId}", productId)
                .then().log().all().extract();
    }

    static ExtractableResponse<Response> findCartProducts() {
        String email = "woowa1@woowa.com";
        String password = "1234";

        String accessToken = AuthIntegrationSupporter
                .login(email, password)
                .as(LoginResponse.class)
                .getAccessToken();

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/products")
                .then().log().all().extract();
    }

    static ExtractableResponse<Response> deleteCartProduct(Long id) {
        String email = "woowa1@woowa.com";
        String password = "1234";

        String accessToken = AuthIntegrationSupporter
                .login(email, password)
                .as(LoginResponse.class)
                .getAccessToken();

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/{id}", id)
                .then().log().all().extract();
    }
}
