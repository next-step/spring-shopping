package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.acceptance.helper.RestHelper;
import shopping.dto.request.LoginRequest;

class AuthAcceptanceTest extends AcceptanceTest {

    // TODO: 언해피케이스
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
}
