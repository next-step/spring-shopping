package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.request.OrderRequest;
import shopping.dto.response.LoginResponse;

public class OrderIntegrationSupporter {

    static ExtractableResponse<Response> order(final OrderRequest orderItems) {
        final String accessToken = getAccessToken();

        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .body(orderItems)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/order")
            .then().log().all().extract();
    }

    static ExtractableResponse<Response> findMemberOrderById(final long id) {
        final String accessToken = getAccessToken();

        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/order-history/{id}", id)
            .then().log().all().extract();
    }

    static ExtractableResponse<Response> findMemberOrders() {
        final String accessToken = getAccessToken();

        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/order-history")
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
