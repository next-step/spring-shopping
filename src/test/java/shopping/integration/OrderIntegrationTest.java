package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.OrderResponse;
import shopping.integration.config.IntegrationTest;
import shopping.integration.util.AuthUtil;
import shopping.integration.util.CartUtil;
import shopping.integration.util.OrderUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static shopping.integration.util.OrderUtil.ORDER_API_URL;

@IntegrationTest
class OrderIntegrationTest {

    @Test
    @DisplayName("주문 상세 페이지 접속 테스트.")
    void orderPage() {
        RestAssured.given().log().all()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .when().get("/order-detail/1")
                .then().statusCode(HttpStatus.OK.value())
                .log().all();
    }

    @Test
    @DisplayName("주문에 성공한다.")
    void createOrder() {
        // given
        final String accessToken = AuthUtil.accessToken();

        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 2L);

        // when & then
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(ORDER_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", ORDER_API_URL + "/1");
    }

    @Test
    @DisplayName("주문의 상세 정보를 조회한다.")
    void findOrder() {
        // given
        final String accessToken = AuthUtil.accessToken();

        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 2L);

        final String url = OrderUtil.createOrder(accessToken).header("Location");

        // when
        final OrderResponse orderResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(url)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(OrderResponse.class);

        // then
        assertThat(orderResponse.getItems()).hasSize(2);
    }

    @Test
    @DisplayName("존재하지 않는 주문의 상세 정보를 조회할 때 실패한다.")
    void findOrderNotExist() {
        // given
        final String accessToken = AuthUtil.accessToken();

        // when & then
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ORDER_API_URL + "/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("사용자의 주문 목록들을 조회한다.")
    void findOrdersByUser() {
        // given
        final String accessToken = AuthUtil.accessToken();

        CartUtil.createCartItem(accessToken, 1L);
        OrderUtil.createOrder(accessToken);

        CartUtil.createCartItem(accessToken, 2L);
        OrderUtil.createOrder(accessToken);

        // when
        final OrderResponse[] orderResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ORDER_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(OrderResponse[].class);

        // then
        assertThat(orderResponse).hasSize(2);
    }
}
