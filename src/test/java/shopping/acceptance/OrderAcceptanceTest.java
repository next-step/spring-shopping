package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.acceptance.helper.AuthHelper;
import shopping.acceptance.helper.RestHelper;
import shopping.dto.response.OrderCreateResponse;
import shopping.dto.response.OrderDetailResponse;
import shopping.dto.response.OrderHistoryResponse;

@DisplayName("주문 관련 기능 인수 테스트")
class OrderAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("회원은 장바구니에 담긴 상품을 주문할 수 있다.")
    void order() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");

        /* when */
        final ExtractableResponse<Response> response = RestHelper.post("/api/order", jwt);

        /* then */
        // TODO: Created, location
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(OrderCreateResponse.class).getOrderId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("회원은 본인의 주문 상세 내역을 확인할 수 있다.")
    void orderDetail() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");

        /* when */
        final ExtractableResponse<Response> response = RestHelper.get("/api/order/1", jwt);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(OrderDetailResponse.class).getOrderProducts())
            .hasSize(3);
    }

    @Test
    @DisplayName("회원은 본인의 모든 주문 내역을 확인할 수 있다.")
    void orderHistory() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");

        /* when */
        final ExtractableResponse<Response> response = RestHelper.get("/api/order", jwt);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(new TypeRef<List<OrderHistoryResponse>>() {
        })).hasSize(2);
    }
}
