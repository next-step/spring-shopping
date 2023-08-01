package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.ErrorResponse;
import shopping.dto.LoginResponse;
import shopping.infrastructure.JwtProvider;
import shopping.integration.config.IntegrationTest;
import shopping.integration.util.AuthUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

@IntegrationTest
public class AuthIntegrationTest {

    @Autowired
    JwtProvider jwtProvider;

    @Test
    @DisplayName("로그인 페이지 접속 테스트")
    void loginPage() {
        RestAssured.given().log().all()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .when().get("/login")
                .then().statusCode(HttpStatus.OK.value())
                .body(containsString("Login"))
                .log().all();
    }

    @Test
    @DisplayName("로그인에 성공한다.")
    void loginSuccess() {
        // when
        String token = AuthUtil.login("test@gmail.com", "test1234")
                .as(LoginResponse.class)
                .getAccessToken();

        // then
        assertThat(jwtProvider.getPayload(token)).isEqualTo("1");
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인 시 로그인에 실패한다.")
    void loginWithNotExistEmail() {
        // when
        ErrorResponse response = AuthUtil.login("error", "test1234")
                .as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("잘못된 로그인 요청입니다.");
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 시 로그인에 실패한다.")
    void loginWithWrongPassword() {
        // when
        ErrorResponse response = AuthUtil.login("test@gmail.com", "error")
                .as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("잘못된 로그인 요청입니다.");
    }
}
