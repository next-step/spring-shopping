package shopping.intergration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import shopping.auth.JwtTokenProvider;
import shopping.dto.response.LoginResponse;
import shopping.exception.ErrorCode;

import static org.assertj.core.api.Assertions.assertThat;
import static shopping.intergration.helper.LogInHelper.loginRequest;
import static shopping.intergration.helper.RestAssuredHelper.extractObject;

class LoginIntegrationTest extends IntegrationTest {

    private static final String EMAIL = "yongs170@naver.com";
    private static final String PASSWORD = "123!@#asd";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("올바른 이메일과 비밀번호가 들어오는 경우 로그인이 성공한다.")
    void login() {
        final ExtractableResponse<Response> response = loginRequest(EMAIL, PASSWORD);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(jwtTokenProvider.validateToken(extractObject(response, LoginResponse.class).getToken())).isTrue();
    }

    @Test
    @DisplayName("입력 이메일이 없는 경우 로그인에 실패하다.")
    void loginFailBecauseOfEmail() {
        final String invalidEmail = "yyyy@naver.com";

        final ExtractableResponse<Response> response = loginRequest(invalidEmail, PASSWORD);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo(ErrorCode.NOT_FOUND_MEMBER_EMAIL.getMessage());
    }

    @Test
    @DisplayName("패스워드가 틀린 경우 로그인에 실패하다.")
    void loginFailBecauseOfPassword() {
        final String invalidPassword = "123!@#a";

        final ExtractableResponse<Response> response = loginRequest(EMAIL, invalidPassword);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo(ErrorCode.PASSWORD_NOT_MATCH.getMessage());
    }
}
