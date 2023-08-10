package shopping.app.accept;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.app.accept.UrlHelper.Auth;
import shopping.auth.dto.LoginRequest;

@DisplayName("Auth 인수테스트")
class AuthAcceptanceTest extends AcceptanceTest {

    private static final LoginRequest ADMIN_REQUEST = new LoginRequest("admin@hello.world", "admin!123");

    @Test
    @DisplayName("POST /login/token API는 로그인에 성공하면, 토큰을 반환한다")
    void it_return_jwt_when_login_success() {
        // when
        ExtractableResponse<Response> jwt = Auth.login(ADMIN_REQUEST);

        // then
        AssertHelper.Auth.assertJwt(jwt);
    }

    @Test
    @DisplayName("POST /login/token API는 로그인에 실패하면, AUTH-401 예외를 던진다")
    void it_throw_auth_401_when_login_failed() {
        // given
        LoginRequest loginRequest = new LoginRequest(ADMIN_REQUEST.getEmail(), "incorrect");

        // when
        ExtractableResponse<Response> jwt = Auth.login(loginRequest);

        // then
        AssertHelper.Auth.assertLoginFailed(jwt);
    }
}
