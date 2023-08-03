package shopping.acceptance.helper;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.request.LoginRequest;

public class AuthHelper {

    private AuthHelper() {
        throw new UnsupportedOperationException();
    }

    public static String login(
        final String email, final String password
    ) {
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(new LoginRequest(email, password))
            .when().post("/api/login")
            .then().log().all()
            .extract();

        return response.jsonPath().getString("accessToken");
    }
}
