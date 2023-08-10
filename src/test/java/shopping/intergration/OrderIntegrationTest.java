package shopping.intergration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import shopping.dto.response.OrderCreateResponse;
import shopping.dto.response.OrderResponse;
import shopping.dto.response.OrderResponses;
import shopping.exception.ApiExceptionResponse;
import shopping.exception.ErrorCode;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderItemRepository;
import shopping.repository.OrderRepository;

import static org.assertj.core.api.Assertions.*;
import static shopping.intergration.helper.CartItemHelper.addCartItem;
import static shopping.intergration.helper.LogInHelper.login;
import static shopping.intergration.helper.OrderHelper.*;
import static shopping.intergration.helper.RestAssuredHelper.extractObject;
import static shopping.intergration.utils.LoginUtils.EMAIL;
import static shopping.intergration.utils.LoginUtils.PASSWORD;


@DisplayName("주문 테스트")
class OrderIntegrationTest extends IntegrationTest {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        cartItemRepository.deleteAllInBatch();
        orderItemRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("장바구니에 담긴 아이템을 주문한다.")
    void createOrder() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);

        final ExtractableResponse<Response> response = createOrderRequest(accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("사용자가 주문한 주문 상세 정보를 조회한다.")
    void readOrder() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);
        final OrderCreateResponse orders = createOrders(accessToken);

        final ExtractableResponse<Response> response = readOrderRequest(accessToken, orders);


        final OrderResponse orderResponse = extractObject(response, OrderResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(orderResponse.getOrderId()).isNotNull();
        assertThat(orderResponse.getOrderPrice()).isEqualTo(23000);
        assertThat(orderResponse.getExchangeRate()).isCloseTo(1300.0, within(0.001));
        assertThat(orderResponse.getExchangedPrice()).isCloseTo(23000 / 1300.0, within(0.001));
        assertThat(orderResponse.getOrderItems())
                .hasSize(2)
                .extracting("image", "name", "price", "quantity")
                .contains(
                        tuple("/image/Chicken.png", "Chicken", 10000, 1),
                        tuple("/image/Pizza.png", "Pizza", 13000, 1)
                );

    }


    @Test
    @DisplayName("사용자가 주문한 주문 목록을 모두 조회한다.")
    void readOrders() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);
        createOrders(accessToken);
        addCartItem(accessToken, 2L);
        addCartItem(accessToken, 2L);
        createOrders(accessToken);

        final ExtractableResponse<Response> response = readOrdersRequest(accessToken);

        final OrderResponses orderResponses = extractObject(response, OrderResponses.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(orderResponses.getOrders())
                .hasSize(2)
                .extracting("orderPrice", "exchangedPrice")
                .contains(
                        tuple(23000L, Math.round((23000 / 1300.0) * 1000) / 1000.0),
                        tuple(26000L, Math.round((26000 / 1300.0) * 1000) / 1000.0)
                );
    }

    @Test
    @DisplayName("장바구니에 등록한 아이템이 없으면 주문에 실패한다.")
    void ifEmptyCartFailCreateOrder() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();

        final ExtractableResponse<Response> response = createOrderRequest(accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(extractObject(response, ApiExceptionResponse.class).getMessage())
                .isEqualTo(ErrorCode.EMPTY_CART_ITEM.getMessage());
    }
}
