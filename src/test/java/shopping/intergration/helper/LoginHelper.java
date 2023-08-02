package shopping.intergration.helper;

import io.restassured.RestAssured;
import org.springframework.http.MediaType;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;

public class LoginHelper {

    public static String login(final String email, final String password) {
        return RestAssured
                .given().log().all()
                .body(new LoginRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(LoginResponse.class).getToken();
    }
}
