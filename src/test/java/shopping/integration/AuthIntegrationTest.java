package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

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
}
