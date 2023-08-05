package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.LoginRequest;

@DisplayName("주문 기능 인수테스트")
class OrderIntegrationTest extends IntegrationTest {

    @DisplayName("주문 요청시 장바구니에있는 아이템 구매 및 장바구니 비우기")
    @Test
    void orderThenCreateOrderAndCartEmpty() {
        // given
        CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
        String accessToken = RestAssured
                .given().log().all()
                .body(new LoginRequest("admin@example.com", "123456789"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .extract().jsonPath().getString("accessToken");

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequest)
                .when().post("/cart/items")
                .then().log().all()
                .extract();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/orders")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotEmpty();
    }
}
