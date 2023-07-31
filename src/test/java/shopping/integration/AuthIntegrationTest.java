package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.LoginRequest;

class AuthIntegrationTest extends IntegrationTest {

    @DisplayName("로그인 페이지 연동")
    @Test
    void loginPage() {
        // when, then
        RestAssured
                .given().log().all()
                .when().get("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("정상 로그인 요청")
    @Test
    void whenLoginSuccessThenGetToken() {
        // given
        LoginRequest loginRequest = new LoginRequest("test@example.com", "1234");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        // TODO: 토큰 유효성 검사
    }

    @DisplayName("이메일이 null일시 BadRequest 반환")
    @Test
    void nullEmail() {
        // given
        LoginRequest loginRequest = new LoginRequest(null, "1234");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호가 null일시 BadRequest 반환")
    @Test
    void nullPassword() {
        // given
        LoginRequest loginRequest = new LoginRequest("test@example.com", null);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이메일이 빈 문자열 일시 BadRequest 반환")
    @Test
    void blankEmail() {
        // given
        LoginRequest loginRequest = new LoginRequest("  ", "1234");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호가 빈 문자열 일시 BadRequest 반환")
    @Test
    void blankPassword() {
        // given
        LoginRequest loginRequest = new LoginRequest("test@example.com", "   ");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이메일이 100자 이상일시 BadRequest 반환")
    @Test
    void longEmail() {
        // given
        LoginRequest loginRequest = new LoginRequest("test@example.com".repeat(100), "1234");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호가 100자 이상일시 BadRequest 반환")
    @Test
    void longPassword() {
        // given
        LoginRequest loginRequest = new LoginRequest("test@example.com", "1234".repeat(100));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("등록되지 않은 이메일일시 BadRequest 반환")
    @Test
    void notRegisteredEmail() {
        // given
        LoginRequest loginRequest = new LoginRequest("wrongemail@example.com", "1234");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호를 틀렸을 시 BadRequest 반환")
    @Test
    void wrongPassword() {
        // given
        LoginRequest loginRequest = new LoginRequest("test@example.com", "wrongpassword");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
