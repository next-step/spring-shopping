package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.ErrorResponse;
import shopping.dto.LoginResponse;
import shopping.dto.OrderDetailResponse;
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
    @DisplayName("장바구니에 상품이 없으면 주문 생성 시 오류를 반환한다.")
    void createOrderNoItem() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();

        // when
        ErrorResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/orders")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("장바구니에 상품이 없습니다.");
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

    @Test
    @DisplayName("상세 주문 내역을 조회할 수 있다.")
    void findDetail() {
        //given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();

        CartUtil.createCartItem(accessToken, 2L);
        CartUtil.createCartItem(accessToken, 2L);
        CartUtil.createCartItem(accessToken, 2L);
        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 1L);
        Long orderId = OrderUtil.createOrder(accessToken);

        // when
        OrderDetailResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/orders/" + orderId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(OrderDetailResponse.class);

        // then
        assertThat(response.getId()).isEqualTo(orderId);
        assertThat(response.getTotalPrice()).isEqualTo(115000);
        assertThat(response.getExchangeRate()).isEqualTo(1.0);
    }

    @Test
    @DisplayName("존재하지 않는 주문번호 입력시 오류를 반환한다.")
    void findDetailOrderNotExist() {
        //given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();

        // when
        ErrorResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/orders/" + 3L)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("존재하지 않는 주문 번호입니다 : 3");
    }

    @Test
    @DisplayName("권한이 없는 주문번호에 접근 시 오류를 반환한다.")
    void findDetailOrderNotAllowed() {
        //given
        String user1AccessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        String user2AccessToken = AuthUtil.login("test2@gmail.com", "test1234")
                .as(LoginResponse.class).getAccessToken();

        CartUtil.createCartItem(user1AccessToken, 2L);
        CartUtil.createCartItem(user1AccessToken, 2L);
        CartUtil.createCartItem(user1AccessToken, 2L);
        CartUtil.createCartItem(user1AccessToken, 1L);
        CartUtil.createCartItem(user1AccessToken, 1L);
        Long orderId = OrderUtil.createOrder(user1AccessToken);

        // when
        ErrorResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(user2AccessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/orders/" + orderId)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract().as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("주문 접근 권한이 없습니다.");
    }
}
