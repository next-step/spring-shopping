package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.dto.response.ErrorResponse;
import shopping.dto.response.LoginResponse;

@DisplayName("로그인 기능")
class AuthIntegrationTest extends IntegrationTest {


    @DisplayName("이메일과 비밀번호로 로그인을 한다")
    @Test
    void login() {
        // given
        String email = "woowa1@woowa.com";
        String password = "1234";

        // when
        ExtractableResponse<Response> response = AuthIntegrationSupporter.login(email, password);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        LoginResponse loginResponse = response.body().as(LoginResponse.class);
        assertThat(loginResponse.getAccessToken()).isNotBlank();
    }

    @DisplayName("이메일이 공백이면 BAD REQUEST 상태를 반환한다.")
    @Test
    void returnBadRequest_WhenEmailIsBlank() {
        // given
        String jsonRequest = createJsonRequest("", "1234");

        // when
        ExtractableResponse<Response> response = AuthIntegrationSupporter.loginWithJson(jsonRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().as(ErrorResponse.class).getMessage())
            .isEqualTo("이메일 입력이 존재하지 않습니다");
    }

    @DisplayName("비밀번호가 공백이면 BAD REQUEST 상태를 반환한다.")
    @Test
    void returnBadRequest_WhenPasswordIsBlank() {
        // given
        String jsonRequest = createJsonRequest("woowa1@woowa.com", "");

        // when
        ExtractableResponse<Response> response = AuthIntegrationSupporter.loginWithJson(jsonRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().as(ErrorResponse.class).getMessage())
            .isEqualTo("비밀번호 입력이 존재하지 않습니다");
    }

    @DisplayName("비밀번호가 일치하지 않으면 UNAUTHORIZED 상태를 반환한다.")
    @Test
    void returnStatusUnauthorized_WhenPasswordIsNotMatch() {
        // given
        String email = "woowa1@woowa.com";
        String invalidPassword = "invalid";

        // when
        ExtractableResponse<Response> response = AuthIntegrationSupporter.login(email,
            invalidPassword);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("이메일과 일치하는 사용자가 없으면 UNAUTHORIZED 상태를 반환한다.")
    @Test
    void returnStatus400_WhenPasswordIsNotMatch() {
        // given
        String email = "notExist@woowa.com";
        String invalidPassword = "1234";

        // when
        ExtractableResponse<Response> response = AuthIntegrationSupporter.login(email,
            invalidPassword);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private String createJsonRequest(String email, String password) {
        return "{\"email\":" + "\"" + email + "\","
            + "\"password\":" + "\"" + password + "\"}";
    }
}
