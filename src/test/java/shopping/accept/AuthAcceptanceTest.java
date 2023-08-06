package shopping.accept;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.accept.UrlHelper.Auth;
import shopping.auth.dto.LoginRequest;

@DisplayName("Auth 인수테스트")
class AuthAcceptanceTest extends AcceptanceTest {

    private static final LoginRequest ADMIN_REQUEST = new LoginRequest("admin@hello.world", "admin!123");

    @Test
    @DisplayName("POST /login/token API는 로그인에 성공하면, 토큰을 반환한다")
    void it_return_jwt_when_login_success() {
        // when
        ExtractableResponse<Response> result = Auth.login(ADMIN_REQUEST);

        // then
        AssertHelper.Auth.assertJwt(result);
    }

    @Test
    @DisplayName("POST /login/token API는 로그인에 실패하면, Bad Request 예외를 던진다")
    void it_throw_Bad_Request_when_login_failed() {
        // given
        LoginRequest loginRequest = new LoginRequest(ADMIN_REQUEST.getEmail(), "incorrect");

        // when
        ExtractableResponse<Response> result = Auth.login(loginRequest);

        // then
        AssertHelper.Http.assertIsBadRequest(result);
    }
}
