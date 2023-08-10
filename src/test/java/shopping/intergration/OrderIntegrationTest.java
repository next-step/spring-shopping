package shopping.intergration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static shopping.exception.ShoppingErrorType.INVALID_ORDER_REQUEST;
import static shopping.intergration.helper.RestAssuredHelper.addCartItem;
import static shopping.intergration.helper.RestAssuredHelper.addOrder;
import static shopping.intergration.helper.RestAssuredHelper.extractList;
import static shopping.intergration.helper.RestAssuredHelper.extractObject;
import static shopping.intergration.helper.RestAssuredHelper.login;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.response.OrderHistoryResponse;
import shopping.dto.response.OrderResponse;
import shopping.repository.OrderRepository;

class OrderIntegrationTest extends IntegrationTest {

    private static final String EMAIL = "yongs170@naver.com";
    private static final String PASSWORD = "123!@#asd";

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("장바구니에 있던 상품들을 주문한다.")
    void createOrder() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);
        addCartItem(accessToken, 2L);

        final ExtractableResponse<Response> response = RestAssured.given().log().all().auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE).when()
                .post("/api/orders").then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        final OrderResponse orderResponse = extractObject(response, OrderResponse.class);
        assertThat(orderResponse.getOrderItems()).extracting("name", "image", "price", "quantity")
                .contains(
                        tuple("Chicken", "/image/Chicken.png", 10_000, 3),
                        tuple("Pizza", "/image/Pizza.png", 13_000, 2)
                );
    }

    @Test
    @DisplayName("장바구니에 상품이 없는 경우 예외가 발생한다.")
    void createOrderWithEmptyCartItems() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();

        final ExtractableResponse<Response> response = RestAssured.given().log().all().auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE).when()
                .post("/api/orders").then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(INVALID_ORDER_REQUEST.getMessage());
    }

    @Test
    @DisplayName("주문 번호를 통해 주문 상세 정보를 확인한다.")
    void getOrderDetails() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);
        addCartItem(accessToken, 2L);
        final Long orderId = addOrder(accessToken).getOrderId();

        final ExtractableResponse<Response> response = RestAssured.given().log().all().auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE).when().get("/api/orders/" + orderId).then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final OrderResponse orderResponse = extractObject(response, OrderResponse.class);
        assertThat(orderResponse.getOrderItems()).extracting("name", "image", "price", "quantity")
                .contains(
                        tuple("Chicken", "/image/Chicken.png", 10_000, 3),
                        tuple("Pizza", "/image/Pizza.png", 13_000, 2)
                );
    }

    @Test
    @DisplayName("주문 목록을 확인한다.")
    void getOrderHistories() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);
        addCartItem(accessToken, 2L);
        addOrder(accessToken);
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);
        addOrder(accessToken);

        final ExtractableResponse<Response> response = RestAssured.given().log().all().auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE).when().get("/api/orders").then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final List<OrderHistoryResponse> orderHistoryResponses = extractList(response, OrderHistoryResponse.class);
        assertThat(orderHistoryResponses).hasSize(2);
        assertThat(orderHistoryResponses.get(0).getOrderItems()).extracting("name", "image", "price", "quantity")
                .contains(
                        tuple("Chicken", "/image/Chicken.png", 10_000, 2),
                        tuple("Pizza", "/image/Pizza.png", 13_000, 2)
                );
        assertThat(orderHistoryResponses.get(1).getOrderItems()).extracting("name", "image", "price", "quantity")
                .contains(
                        tuple("Chicken", "/image/Chicken.png", 10_000, 1),
                        tuple("Pizza", "/image/Pizza.png", 13_000, 1)
                );
    }
}
