package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.LoginResponse;
import shopping.dto.OrderResponse;
import shopping.integration.config.IntegrationTest;
import shopping.integration.util.AuthUtil;
import shopping.integration.util.CartUtil;
import shopping.integration.util.OrderUtil;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class OrderIntegrationTest {

    @Test
    @DisplayName("장바구니 데이터로 주문을 생성할 수 있다.")
    void createOrder() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 2L);

        // when
        Long id = OrderUtil.createOrder(accessToken);

        // then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    @DisplayName("주문 목록을 조회할 수 있다.")
    void findAll() {
        //given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 2L);
        OrderUtil.createOrder(accessToken);

        CartUtil.createCartItem(accessToken, 2L);
        CartUtil.createCartItem(accessToken, 2L);
        OrderUtil.createOrder(accessToken);

        // when
        OrderResponse[] orderResponses = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/orders")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(OrderResponse[].class);

        // then
        assertThat(Stream.of(orderResponses).map(OrderResponse::getId)).containsAll(List.of(1L, 2L));
    }
}
