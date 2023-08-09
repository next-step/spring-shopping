package shopping.integration.util;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.LoginResponse;

import java.util.HashMap;
import java.util.Map;

public class AuthUtil {

    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String TEST_PASSWORD = "test1234";

    public static String accessToken() {
        return login().as(LoginResponse.class).getAccessToken();
    }

    public static ExtractableResponse<Response> login(String email, String password) {
        final Map<String, String> request = new HashMap<>();
        request.put("email", email);
        request.put("password", password);

        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> login() {
        return login(TEST_EMAIL, TEST_PASSWORD);
    }
}
