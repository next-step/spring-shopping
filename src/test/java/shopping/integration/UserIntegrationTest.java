package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.auth.dto.request.LoginRequest;
import shopping.auth.dto.response.LoginResponse;
import shopping.common.exception.ErrorCode;
import shopping.common.exception.ErrorResponse;

class UserIntegrationTest extends IntegrationTest {

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("로그인에 성공한다.")
    void loginSuccess() {
        /* given */
        final LoginRequest loginRequest = new LoginRequest("test_email@woowafriends.com",
            "test_password!");

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(loginRequest)
            .when().post("/login/token")
            .then().log().all()
            .extract();

        /* then */
        final LoginResponse loginResponse = response.as(LoginResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(loginResponse.getAccessToken()).isNotBlank();
    }

    @Test
    @DisplayName("로그인에 실패한다. - 이메일이 존재하지 않는다.")
    void loginFailEmailNotRegistered() {
        /* given */
        final LoginRequest loginRequest = new LoginRequest("invalid_email",
            "test_password!");

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(loginRequest)
            .when().post("/login/token")
            .then().log().all()
            .extract();

        /* then */
        final ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_EMAIL);
    }

    @Test
    @DisplayName("로그인에 실패한다. - 비밀번호가 일치하지 않는다.")
    void loginFailPasswordNotValid() {
        /* given */
        final LoginRequest loginRequest = new LoginRequest("test_email@woowafriends.com",
            "invalid_password");

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(loginRequest)
            .when().post("/login/token")
            .then().log().all()
            .extract();

        /* then */
        final ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD);

    }
}
