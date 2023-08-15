package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.model.Header;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.OrderResponse;

@DisplayName("주문 기능 인수테스트")
@ExtendWith(MockServerExtension.class)
class OrderIntegrationTest extends IntegrationTest {

    private static ClientAndServer mockServer;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("secret.currency.url",
                () -> "http://127.0.0.1:" + mockServer.getPort() + "/live?access_key=");
    }

    @BeforeAll
    static void setUpMockServer() {
        mockServer = ClientAndServer.startClientAndServer();
        mockServer.when(request()
                .withMethod("GET")
                .withPath("/live")
        ).respond(response()
                .withStatusCode(200)
                .withHeader(new Header("Content-Type",
                        MediaType.APPLICATION_JSON_VALUE))
                .withBody(
                        "{\"success\":true,\"terms\":\"https://localhost/terms\",\"privacy\":\"https://localhost.com/privacy\",\"timestamp\":1691490183,\"source\":\"USD\",\"quotes\":{\"USDKRW\":1318.400853}}")
        );
    }

    @AfterAll
    static void shutDownMockServer() {
        mockServer.stop();
    }

    @DisplayName("주문 요청시 장바구니에있는 아이템 구매 및 장바구니 비우기")
    @Test
    void orderThenCreateOrderAndCartEmpty() {
        // given
        CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
        String accessToken = login();

        createCartItem(cartItemCreateRequest, accessToken);

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

    @DisplayName("주문 상세 정보 요청 시 상세 정보 반환")
    @Test
    void orderDetailThenGetOrderResponse() {
        // given
        CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
        CartItemCreateRequest cartItemCreateRequest2 = new CartItemCreateRequest(2L);
        String accessToken = login();
        createCartItem(cartItemCreateRequest, accessToken);
        createCartItem(cartItemCreateRequest2, accessToken);

        ExtractableResponse<Response> createdResponse = createOrder(accessToken);
        String[] location = createdResponse.header("Location").split("/");
        String orderId = location[location.length - 1];

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/orders/" + orderId)
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(OrderResponse.class).getOrderItems()).hasSize(2);
    }

    @DisplayName("주문 전체 정보 요청 시 전체 정보 반환")
    @Test
    void orderAllThenGetPageOfOrderResponse() {
        // given
        CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
        CartItemCreateRequest cartItemCreateRequest2 = new CartItemCreateRequest(2L);
        String accessToken = login();

        createCartItem(cartItemCreateRequest, accessToken);
        createOrder(accessToken);
        createCartItem(cartItemCreateRequest, accessToken);
        createCartItem(cartItemCreateRequest2, accessToken);
        createOrder(accessToken);
        createCartItem(cartItemCreateRequest2, accessToken);
        createOrder(accessToken);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/orders")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong("totalElements")).isEqualTo(3L);
    }

    private static String login() {
        return RestAssured
                .given().log().all()
                .body(new LoginRequest("admin@example.com", "123456789"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .extract().jsonPath().getString("accessToken");
    }

    private static void createCartItem(CartItemCreateRequest cartItemCreateRequest,
            String accessToken) {
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequest)
                .when().post("/cart/items")
                .then().log().all()
                .extract();
    }

    private static ExtractableResponse<Response> createOrder(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/orders")
                .then().log().all()
                .extract();
    }
}
