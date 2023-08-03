package shopping.integration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("비밀번호가 일치하지 않으면 UNAUTHORIZED 상태를 반환한다.")
    @Test
    void returnStatusUnauthorized_WhenPasswordIsNotMatch() {
        // given
        String email = "woowa1@woowa.com";
        String invalidPassword = "invalid";

        // when
        ExtractableResponse<Response> response = AuthIntegrationSupporter.login(email, invalidPassword);

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
        ExtractableResponse<Response> response = AuthIntegrationSupporter.login(email, invalidPassword);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
