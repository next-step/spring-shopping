package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.dto.request.OrderRequest;

@DisplayName("주문 기능")
class OrderIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("장바구니 아이템 주문에 성공한다")
    void order() {
        // given
        OrderRequest orderRequest = new OrderRequest(List.of());

        // when
        ExtractableResponse<Response> result = OrderIntegrationSupporter.order(orderRequest);

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(result.header("Location")).isNotBlank();
    }

    @Test
    @DisplayName("주문 상세 정보를 조회한다")
    void findMemberOrderById() {
        // given
        long orderId = 1;

        // when
        ExtractableResponse<Response> result = OrderIntegrationSupporter.findMemberOrderById(
            orderId);

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("사용자가 주문하지 않은 주문 상세 정보를 조회하면 Bad Request 상태를 반환한다")
    void returnBadRequest_WhenMemberIdIsNotMatch() {
        // given
        long notMatchOrderId = 2;

        // when
        ExtractableResponse<Response> result = OrderIntegrationSupporter.findMemberOrderById(
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
        ExtractableResponse<Response> result = OrderIntegrationSupporter.findMemberOrderById(
            notExistOrderId);

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("사용자가 주문 목록을 반환한다")
    void findMemberOrders() {
        // when
        ExtractableResponse<Response> result = OrderIntegrationSupporter.findMemberOrders();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
