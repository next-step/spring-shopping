package shopping.acceptance.helper;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import shopping.auth.dto.request.LoginRequest;
import shopping.auth.dto.response.LoginResponse;

public class AuthHelper {

    private AuthHelper() {
        throw new UnsupportedOperationException();
    }

    public static String login(
        final String email, final String password
    ) {
        final ExtractableResponse<Response> response = RestHelper
            .post("/api/login", new LoginRequest(email, password));

        return response.body().as(LoginResponse.class).getAccessToken();
    }
}
