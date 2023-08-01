package shopping.intergration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.ErrorCode;
import shopping.security.JwtTokenProvider;

class LoginIntegrationTest extends IntegrationTest {

    private static final String EMAIL = "yongs170@naver.com";
    private static final String PASSWORD = "123!@#asd";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("올바른 이메일과 비밀번호가 들어오는 경우 로그인이 성공한다.")
    void login() {
        String accessToken = RestAssured
                .given().log().all()
                .body(new LoginRequest(EMAIL, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(LoginResponse.class).getToken();

        assertThat(jwtTokenProvider.validateToken(accessToken)).isTrue();
    }

    @Test
    @DisplayName("입력 이메일이 없는 경우 로그인에 실패하다.")
    void loginFailBecauseOfEmail() {
        final String invalidEmail = "yyyy@naver.com";

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(new LoginRequest(invalidEmail, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo(ErrorCode.NOT_FOUND_MEMBER_EMAIL.getMessage());
    }

    @Test
    @DisplayName("패스워드가 틀린 경우 로그인에 실패하다.")
    void loginFailBecauseOfPassword() {
        final String invalidPassword = "123!@#a";

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(new LoginRequest(EMAIL, invalidPassword))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo(ErrorCode.PASSWORD_NOT_MATCH.getMessage());
    }
}
