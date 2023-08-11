package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import shopping.domain.ExchangeCode;
import shopping.dto.response.ExchangeResponse;
import shopping.dto.response.OrderResponse;
import shopping.infrastructure.CustomRestTemplate;

@DisplayName("주문 기능")
class OrderIntegrationTest extends IntegrationTest {

    @MockBean
    CustomRestTemplate mockCustomRestTemplate;

    @Test
    @DisplayName("장바구니 아이템 주문에 성공한다")
    void order() {
        // given
        when(mockCustomRestTemplate.getResult(anyString(), any()))
            .thenReturn(createExchangeResponse());

        // when
        ExtractableResponse<Response> result = OrderIntegrationSupporter.order();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(result.header("Location")).isNotBlank();
    }

    @Test
    @DisplayName("장바구니 아이템 주문에 성공하면 기존 장바구니 아이템들은 모두 제거된다")
    void allCartProductsShouldBeDeleted_WhenOrderSuccess() {
        // given
        when(mockCustomRestTemplate.getResult(anyString(), any()))
            .thenReturn(createExchangeResponse());

        // when
        OrderIntegrationSupporter.order();

        // then
        ExtractableResponse<Response> cartProducts = CartProductIntegrationSupporter.findCartProducts();
        assertThat(cartProducts.body().as(ArrayList.class)).isEmpty();
    }

    @Test
    @DisplayName("장바구니에 아이템이 없으면 Bad Request 를 반환한다")
    void returnBadRequest_WhenCartProductIsEmpty() {
        // given
        when(mockCustomRestTemplate.getResult(anyString(), any()))
            .thenReturn(createExchangeResponse());

        // when
        ExtractableResponse<Response> result = OrderIntegrationSupporter.order("woowa2@woowa.com",
            "1234");

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("주문 상세 정보를 조회한다")
    void findMemberOrderById() {
        // given
        when(mockCustomRestTemplate.getResult(anyString(), any()))
            .thenReturn(createExchangeResponse());

        ExtractableResponse<Response> order = OrderIntegrationSupporter.order();
        String orderId = order.header("Location").split("/")[2];

        // when
        ExtractableResponse<Response> result = OrderIntegrationSupporter.findOrderById(
            Long.parseLong(orderId));

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.body().as(OrderResponse.class).getExchangeRate()).isPositive();
    }

    @Test
    @DisplayName("사용자가 주문하지 않은 주문 상세 정보를 조회하면 Bad Request 상태를 반환한다")
    void returnBadRequest_WhenMemberIdIsNotMatch() {
        // given
        long notMatchOrderId = 2;

        // when
        ExtractableResponse<Response> result = OrderIntegrationSupporter.findOrderById(
            notMatchOrderId);

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("주문 id 에 해당하는 주문 정보가 존재하지 않으면 Bad Request 상태를 반환한다")
    void returnBadRequest_WhenOrderIdNotExist() {
        // given
        long notExistOrderId = 10;

        // when
        ExtractableResponse<Response> result = OrderIntegrationSupporter.findOrderById(
            notExistOrderId);

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("사용자가 주문 목록을 반환한다")
    void findMemberOrders() {
        // given
        when(mockCustomRestTemplate.getResult(anyString(), any()))
            .thenReturn(createExchangeResponse());

        OrderIntegrationSupporter.order();

        // when
        ExtractableResponse<Response> result = OrderIntegrationSupporter.findMemberOrders();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.body().as(ArrayList.class)).hasSize(1);
    }

    private ExchangeResponse createExchangeResponse() {
        Map<String, Double> map = new HashMap<>();
        map.put(ExchangeCode.USDKRW.name(), 1302.2);
        return new ExchangeResponse(map);
    }
}
