package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.text.MessageFormat;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.TestHelper;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.dto.response.OrderIdResponse;
import shopping.dto.response.OrderResponse;
import shopping.exception.ErrorCode;
import shopping.exception.ErrorResponse;

@DisplayName("OrderIntegrationTest")
public class OrderIntegrationTest extends IntegrationTest{

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("성공 : 장바구니 상품 주문 완료")
    void successOrder() {
        // given
        LoginRequest loginRequest = new LoginRequest("test_email@woowafriends.com",
            "test_password!");
        String accessToken = TestHelper.login(loginRequest).as(LoginResponse.class)
            .getAccessToken();
        CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(1L);
        TestHelper.addCartItem(accessToken, cartItemAddRequest);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/order")
            .then()
            .log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("실패 : 장바구니에 상품이 없다면 주문 불가")
    void failOrder() {
        // given
        LoginRequest loginRequest = new LoginRequest("test_email@woowafriends.com",
            "test_password!");
        String accessToken = TestHelper.login(loginRequest).as(LoginResponse.class)
            .getAccessToken();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/order")
            .then()
            .log().all().extract();

        // then
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_PURCHASE);
    }

    @Test
    @DisplayName("성공 : 주문 번호로 주문 조회")
    void searchOrderById() {
        // given
        LoginRequest loginRequest = new LoginRequest("test_email@woowafriends.com",
            "test_password!");
        String accessToken = TestHelper.login(loginRequest).as(LoginResponse.class)
            .getAccessToken();
        CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(1L);
        TestHelper.addCartItem(accessToken, cartItemAddRequest);
        Long orderId = TestHelper.orderCartItems(
            accessToken).as(OrderIdResponse.class).getOrderId();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(MessageFormat.format("/order/{0}", orderId))
            .then()
            .log().all().extract();

        // then
        OrderResponse orderResponse = response.as(OrderResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(orderResponse.getOrderItems().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("성공 : 유저가 주문한 주문 목록 전체 조회")
    void searchOrderByUserId() {
        // given
        LoginRequest loginRequest = new LoginRequest("test_email@woowafriends.com",
            "test_password!");
        String accessToken = TestHelper.login(loginRequest).as(LoginResponse.class)
            .getAccessToken();
        CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(1L);
        TestHelper.addCartItem(accessToken, cartItemAddRequest);
        TestHelper.orderCartItems(accessToken);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/order")
            .then()
            .log().all().extract();

        // then
        List<OrderResponse> orderResponses = response.jsonPath().getList(".", OrderResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(orderResponses.size()).isEqualTo(2);
    }

}
