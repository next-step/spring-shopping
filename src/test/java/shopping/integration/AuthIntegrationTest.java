package shopping.integration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("로그인 기능")
public class AuthIntegrationTest extends IntegrationTest {

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

    @DisplayName("이메일 형식이 아닐 경우 로그인을 할 수 없다")
    @Test
    void loginFail_WhenInvalidEmail() {
        // given
        String email = "woowaaaaaaaaaaaaaaaaa";
        String password = "1234";

        // when
        ExtractableResponse<Response> response = AuthIntegrationSupporter.login(email, password);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("존재하지 않는 사용자일 경우 로그인을 할 수 없다")
    @Test
    void loginFail_WhenInvalidMember() {
        // given
        String email = "woowaaaaaaa@naver.com";
        String password = "1234";

        // when
        ExtractableResponse<Response> response = AuthIntegrationSupporter.login(email, password);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("비밀번호가 틀렸을 경우 로그인을 할 수 없다")
    @Test
    void loginFail_WhenIncorrectPassword() {
        // given
        String email = "woowaaaaaaa@naver.com";
        String password = "12345555555";

        // when
        ExtractableResponse<Response> response = AuthIntegrationSupporter.login(email, password);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
