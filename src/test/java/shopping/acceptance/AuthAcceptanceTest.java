package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.request.LoginRequest;

class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("이메일과 비밀번호로 인증에 성공하면 accessToken을 제공한다.")
    void postLogin() {
        /* given */
        final String email = "woowacamp@naver.com";
        final String password = "woowacamp";
        final LoginRequest request = new LoginRequest(email, password);

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/login")
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final String accessToken = response.jsonPath().getString("accessToken");
        assertThat(accessToken).isNotEmpty();
    }
}
