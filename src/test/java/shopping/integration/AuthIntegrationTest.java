package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.response.ErrorResponse;
import shopping.dto.response.LoginResponse;
import shopping.infrastructure.TokenProvider;
import shopping.integration.config.IntegrationTest;
import shopping.integration.util.AuthUtil;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static shopping.integration.util.AuthUtil.LOGIN_API_URL;
import static shopping.integration.util.CartUtil.CART_API_URL;

@IntegrationTest
class AuthIntegrationTest {

    @Autowired
    TokenProvider tokenProvider;

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
        assertThat(tokenProvider.getPayload(token)).isEqualTo("1");
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인 시 로그인에 실패한다.")
    void loginWithNotExistEmail() {
        // when
        ErrorResponse response = AuthUtil.login("error@abc.com", "test1234")
                .as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("잘못된 로그인 요청입니다");
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 시 로그인에 실패한다.")
    void loginWithWrongPassword() {
        // when
        ErrorResponse response = AuthUtil.login("test@gmail.com", "error")
                .as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("잘못된 로그인 요청입니다");
    }

    @Test
    @DisplayName("email이 null인 경우 로그인에 실패한다.")
    void loginWithNullEmail() {
        // given
        Map<String, String> request = new HashMap<>();
        request.put("email", null);
        request.put("password", "abc");

        // when
        ErrorResponse response = RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(LOGIN_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("email은 필수 항목입니다");
    }

    @Test
    @DisplayName("password가 null인 경우 로그인에 실패한다.")
    void loginWithNullPassword() {
        // given
        Map<String, String> request = new HashMap<>();
        request.put("email", "test@woowa.com");
        request.put("password", null);

        // when
        ErrorResponse response = RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(LOGIN_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("password는 필수 항목입니다");
    }

    @Test
    @DisplayName("access token이 존재하지 않는 경우 오류를 반환한다.")
    void noAccessToken() {
        ErrorResponse response = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(CART_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract().as(ErrorResponse.class);

        assertThat(response.getMessage()).isEqualTo("토큰 정보가 없습니다");
    }

    @Test
    @DisplayName("access token type이 올바르지 않은 경우 오류를 반환한다.")
    void invalidAccessTokenType() {
        ErrorResponse response = RestAssured
                .given().log().all()
                .header("Authorization", "abcd aaaa")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(CART_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract().as(ErrorResponse.class);

        assertThat(response.getMessage()).isEqualTo("토큰이 유효하지 않습니다");
    }

    @Test
    @DisplayName("access token이 올바르지 않은 경우 오류를 반환한다.")
    void invalidAccessToken() {
        ErrorResponse response = RestAssured
                .given().log().all()
                .auth().oauth2("abcd")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(CART_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract().as(ErrorResponse.class);

        assertThat(response.getMessage()).isEqualTo("토큰이 유효하지 않습니다");
    }
}
