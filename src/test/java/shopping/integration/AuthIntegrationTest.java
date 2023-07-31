package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;
import shopping.infrastructure.JwtProvider;
import shopping.integration.config.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class AuthIntegrationTest {

    @Autowired
    JwtProvider jwtProvider;

    @Test
    @DisplayName("로그인에 성공한다.")
    void loginSuccess() {
        // when
        LoginResponse response = RestAssured.given().log().all()
                .body(new LoginRequest("test@gmail.com", "test1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(LoginResponse.class);

        // then
        assertThat(jwtProvider.getPayload(response.getAccessToken())).isEqualTo("1");
    }
}
