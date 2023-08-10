package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.acceptance.helper.AuthHelper;
import shopping.acceptance.helper.RestHelper;
import shopping.dto.response.OrderCreateResponse;

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
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(OrderCreateResponse.class).getOrderId()).isEqualTo(3L);
    }
}
