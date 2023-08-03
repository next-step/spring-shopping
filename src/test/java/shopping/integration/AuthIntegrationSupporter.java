package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.request.LoginRequest;

public class AuthIntegrationSupporter {

    static ExtractableResponse<Response> login(String email, String password) {
        return RestAssured
                .given().log().all()
                .body(new LoginRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();
    }
}
