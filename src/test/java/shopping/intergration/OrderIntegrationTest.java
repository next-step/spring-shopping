package shopping.intergration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.dto.response.OrderCreateResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static shopping.intergration.helper.CartItemHelper.addCartItem;
import static shopping.intergration.helper.LogInHelper.login;
import static shopping.intergration.helper.OrderHelper.*;
import static shopping.intergration.utils.LoginUtils.EMAIL;
import static shopping.intergration.utils.LoginUtils.PASSWORD;

@DisplayName("주문 테스트")
class OrderIntegrationTest extends IntegrationTest {

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

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }


    @Test
    @DisplayName("사용자가 주문한 주문 목록을 모두 조회한다.")
    void readOrders() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);
        createOrders(accessToken);
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);
        createOrders(accessToken);

        final ExtractableResponse<Response> response = readOrdersRequest(accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
