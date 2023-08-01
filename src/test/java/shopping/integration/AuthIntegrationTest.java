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

}
