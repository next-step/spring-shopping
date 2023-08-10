package shopping.integration.support;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.LoginResponse;

public class OrderProductIntegrationSupporter {

    public static ExtractableResponse<Response> orderProduct() {
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
            .when().post("/orders")
            .then().log().all().extract();
    }

    public static ExtractableResponse<Response> findOrder(long orderId) {
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
            .when().get("/orders/{orderId}", orderId)
            .then().log().all().extract();
    }

    public static ExtractableResponse<Response> findOrders() {
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
            .when().get("/orders")
            .then().log().all().extract();
    }
}
