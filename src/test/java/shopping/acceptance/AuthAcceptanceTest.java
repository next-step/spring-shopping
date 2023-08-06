package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import shopping.acceptance.helper.RestHelper;
import shopping.dto.request.LoginRequest;
import shopping.exception.dto.ExceptionResponse;

@DisplayName("인증 관련 기능 인수 테스트")
class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("이메일과 비밀번호로 인증에 성공하면 accessToken을 제공한다.")
    void postLogin() {
        /* given */
        final String email = "woowacamp@naver.com";
        final String password = "woowacamp";
        final LoginRequest request = new LoginRequest(email, password);

        /* when */
        final ExtractableResponse<Response> response = RestHelper.post("/api/login", request);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final String accessToken = response.jsonPath().getString("accessToken");
        assertThat(accessToken).isNotEmpty();
    }

    @Test
    @DisplayName("이메일이 존재하지 않으면 \"존재하지 않는 이메일입니다.\", Bad Request로 응답한다.")
    void postLoginFailWithDoesNotExistEmail() {
        /* given */
        final String email = "maratang@naver.com";
        final String password = "woowacamp";
        final LoginRequest request = new LoginRequest(email, password);

        /* when */
        final ExtractableResponse<Response> response = RestHelper.post("/api/login", request);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().as(ExceptionResponse.class).getMessage())
            .isEqualTo("존재하지 않는 이메일입니다. 입력값: " + email);
    }

    @Test
    @DisplayName("이메일 양식에 맞지 않으면 \"회원 이메일이 형식에 맞지 않습니다.\", Bad Request로 응답한다.")
    void postLoginFailWithIsNotProperEmail() {
        /* given */
        final String email = "maratang";
        final String password = "woowacamp";
        final LoginRequest request = new LoginRequest(email, password);

        /* when */
        final ExtractableResponse<Response> response = RestHelper.post("/api/login", request);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().as(ExceptionResponse.class).getMessage())
            .isEqualTo("회원 이메일이 형식에 맞지 않습니다. 입력값: " + email);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 `비밀번호가 일치하지 않습니다.`, Bad Request로 응답한다.")
    void postLoginFailWithDoesNotMatchPassword() {
        /* given */
        final String email = "woowacamp@naver.com";
        final String password = "maratang";
        final LoginRequest request = new LoginRequest(email, password);

        /* when */
        final ExtractableResponse<Response> response = RestHelper.post("/api/login", request);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().as(ExceptionResponse.class).getMessage())
            .isEqualTo("비밀번호가 일치하지 않습니다. 입력값: " + password);
    }

    @Test
    @DisplayName("Authorization 헤더가 없는 경우 \"토큰 헤더가 존재하지 않습니다.\"로 응답한다.")
    void failAuthenticationWithNoAuthorizationHeader() {
        /* given */

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when().get("/api/cartProduct")
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.body().as(ExceptionResponse.class).getMessage())
            .isEqualTo("토큰 헤더가 존재하지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "    "})
    @DisplayName("토큰 값이 없는 경우 \"토큰 값이 존재하지 않습니다.\".로 응답한다.")
    void failAuthenticationWithNoTokenValue(final String value) {
        /* given */

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, value)
            .when().get("/api/cartProduct")
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.body().as(ExceptionResponse.class).getMessage())
            .isEqualTo("토큰 값이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("토큰 값이 양식에 맞지 않는 경우 \"토큰이 Bearer로 시작하지 않습니다.\"로 응답한다.")
    void failAuthenticationWithNotProperTokenFormat() {
        /* given */

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "maratang 123")
            .when().get("/api/cartProduct")
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.body().as(ExceptionResponse.class).getMessage())
            .isEqualTo("토큰이 Bearer로 시작하지 않습니다.");
    }
}
