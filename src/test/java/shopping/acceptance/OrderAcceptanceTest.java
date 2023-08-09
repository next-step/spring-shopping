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
import shopping.cart.dto.response.CartResponse;
import shopping.order.dto.OrderResponse;

public class OrderAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("사용자가 장바구니의 상품을 주문한다")
    void 사용자가_장바구니의_상품을_주문_한다() {
        // given
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");

        // when
        ExtractableResponse<Response> response = RestHelper.post("/api/order", jwt);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(OrderResponse.class)
            .getItems().size())
            .isEqualTo(2);
        ExtractableResponse<Response> cartProductResponse = RestHelper.get(
            "/api/cartProduct", jwt);
        assertThat(cartProductResponse.body().as(new TypeRef<List<CartResponse>>() {
        }))
            .hasSize(0);

    }


    @Test
    @DisplayName("사용자가 주문번호로 주문을 상세 볼 수 있다.")
    void 주문번호_상세_조회_성공() {
        // given
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        ExtractableResponse<Response> orderPostResponse = RestHelper.post("/api/order", jwt);
        Long orderId = orderPostResponse.body().as(OrderResponse.class).getId();

        // when
        ExtractableResponse<Response> response = RestHelper.get("/api/order/" + orderId, jwt);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(OrderResponse.class).getId()).isEqualTo(orderId);
    }

    @Test
    @DisplayName("사용자 별 주문 정보 반환 성공한다")
    void 사용자별로_주문_정보_반환_성공() {
        // given
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        RestHelper.post("/api/order", jwt);

        // when
        ExtractableResponse<Response> response = RestHelper.get("/api/order/", jwt);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(new TypeRef<List<OrderResponse>>() {
        }))
            .hasSize(1);
    }
}