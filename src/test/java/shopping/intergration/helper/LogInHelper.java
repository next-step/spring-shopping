package shopping.intergration.helper;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;

public class LogInHelper {

    public static ExtractableResponse<Response> loginRequest(final String email, final String password) {
        return RestAssured
                .given().log().all()
                .body(new LoginRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();
    }

    public static LoginResponse login(final String email, final String password) {
        return RestAssuredHelper.extractObject(RestAssured
                .given().log().all()
                .body(new LoginRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract(), LoginResponse.class);
    }
}
