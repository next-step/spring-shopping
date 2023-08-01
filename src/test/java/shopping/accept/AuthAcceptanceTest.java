package shopping.accept;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.accept.UrlHelper.Auth;

@DisplayName("Auth 인수테스트")
class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("POST /login/token API는 로그인에 성공하면, 토큰을 반환한다")
    void it_return_jwt_when_login_success() {
        // given
        LoginRequest loginRequest = new LoginRequest("email@email.com", "password12!");

        // when
        ExtractableResponse<Response> jwt = Auth.login(loginRequest);

        // then
        AssertHelper.Auth.assertJwt(jwt);
    }

    @Test
    @DisplayName("POST /login/token API는 로그인에 실패하면, AUTH-401 예외를 던진다")
    void it_throw_auth_401_when_login_failed() {
        // given
        LoginRequest loginRequest = new LoginRequest("email@email.com", "incorrect");

        // when
        ExtractableResponse<Response> jwt = Auth.login(loginRequest);

        // then
        AssertHelper.Auth.assertLoginFailed(jwt);
    }
}
