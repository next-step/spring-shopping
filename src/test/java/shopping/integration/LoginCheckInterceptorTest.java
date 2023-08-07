package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.ErrorResponse;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LoginCheckInterceptor 클래스")
public class LoginCheckInterceptorTest extends IntegrationTest {

    @DisplayName("토큰이 존재하지 않을 경우 UNAUTHORIZED 상태코드를 반환한다")
    @Test
    void ReturnStatusUnauthorized_WhenTokenIsNull() {
        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().none()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/products")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.body().as(ErrorResponse.class).getMessage()).isEqualTo("로그인이 필요한 서비스입니다");
    }

    @DisplayName("토큰이 유효하지 않을 경우 UNAUTHORIZED 상태코드를 반환한다")
    @Test
    void ReturnStatusUnauthorized_WhenInvalidToken() {
        // given
        String invalidToken = "Asdfasdfasdfsdf";

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(invalidToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/products")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.body().as(ErrorResponse.class).getMessage()).isEqualTo("토큰이 유효하지 않습니다");
    }

    @DisplayName("Authorization Header 가 Bearer 형식이 아닐 경우 UNAUTHORIZED 상태코드를 반환한다")
    @Test
    void ReturnStatusUnauthorized_WhenInvalidHeader() {
        // given
        String token = "Asdfasdfasdfsdf";

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/products")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.body().as(ErrorResponse.class).getMessage()).contains("로그인이 필요한 서비스입니다");
    }
}
