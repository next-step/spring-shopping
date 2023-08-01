package shopping.intergration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
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
}
