package shopping.intergration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.response.OrderCreateResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static shopping.intergration.helper.CartItemHelper.addCartItem;
import static shopping.intergration.helper.LogInHelper.login;
import static shopping.intergration.helper.OrderHelper.createOrderRequest;
import static shopping.intergration.helper.OrderHelper.createOrders;
import static shopping.intergration.helper.RestAssuredHelper.extractObject;
import static shopping.intergration.util.LoginUtils.EMAIL;
import static shopping.intergration.util.LoginUtils.PASSWORD;

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
        assertThat(extractObject(response, OrderCreateResponse.class).getOrderId()).isNotNull();
    }

    @Test
    @DisplayName("사용자가 주문한 주문 상세 정보를 조회한다.")
    void readOrder() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);
        final OrderCreateResponse orders = createOrders(accessToken);

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/orders/{orderId}", orders.getOrderId())
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
