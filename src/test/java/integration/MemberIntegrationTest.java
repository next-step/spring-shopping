package integration;

import static integration.helper.CommonRestAssuredUtils.post;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ErrorResponse;
import shopping.member.dto.LoginRequest;
import shopping.member.dto.LoginResponse;

class MemberIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("일치하는 emaill과 password로 로그인을 성공한다")
    void loginSuccess() {
        // given from test_data.sql

        ExtractableResponse<Response> response =
            post("/login", new LoginRequest("hello@woowa.com", "hello1"));

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(LoginResponse.class))
            .extracting("accessToken")
            .isNotNull();
    }

    //일치하지 않는 email로 로그인을 실패한다
    @Test
    @DisplayName("일치하지 않는 email로 로그인을 실패한다")
    void loginFail2() {
        // given from test_data.sql

        ExtractableResponse<Response> response =
            post("/login", new LoginRequest("fail", "hello1"));

        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body().as(ErrorResponse.class)).extracting("message")
            .isEqualTo("로그인 정보가 일치하지 않습니다.");
        assertThat(response.body().as(ErrorResponse.class)).extracting("statusCode").isEqualTo(400);
    }

    @Test
    @DisplayName("일치하지 않는 password로 로그인을 실패한다")
    void loginFail() {
        // given from test_data.sql

        ExtractableResponse<Response> response =
            post("/login", new LoginRequest("hello@woowa.com", "fail"));

        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body().as(ErrorResponse.class)).extracting("message")
            .isEqualTo("로그인 정보가 일치하지 않습니다.");
        assertThat(response.body().as(ErrorResponse.class)).extracting("statusCode").isEqualTo(400);
    }
}
