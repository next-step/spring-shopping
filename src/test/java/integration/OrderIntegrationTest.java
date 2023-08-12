package integration;

import static integration.helper.CommonRestAssuredUtils.DEFAULT_TOKEN;
import static integration.helper.CommonRestAssuredUtils.get;
import static integration.helper.CommonRestAssuredUtils.post;

import integration.helper.CartHelper;
import integration.helper.ProductHelper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.cart.dto.request.CartItemCreationRequest;
import shopping.member.dto.LoginRequest;
import shopping.member.dto.LoginResponse;
import shopping.order.dto.request.OrderCreationRequest;
import shopping.product.dto.request.ProductCreationRequest;

class OrderIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("장바구니의 상품을 바탕으로 order를 생성한다.")
    void create() {
        // given
        // member -> data.sql
        ProductHelper.createProduct(new ProductCreationRequest("피자", "10000", "imageUrl"));
        CartHelper.createCartItem(new CartItemCreationRequest(1L));

        // when
        ExtractableResponse<Response> response = post("/api/orders",
            new OrderCreationRequest(List.of(1L)), DEFAULT_TOKEN);

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("유저의 상품 상세 정보를 조회한다.")
    void orderDetail() {
        // given
        // member -> data.sql
        ProductHelper.createProduct(new ProductCreationRequest("피자", "10000", "imageUrl"));
        CartHelper.createCartItem(new CartItemCreationRequest(1L));
        post("/api/orders", new OrderCreationRequest(List.of(1L)), DEFAULT_TOKEN);

        // when
        ExtractableResponse<Response> response = get("/api/orders/{orderId}", 1L, DEFAULT_TOKEN);

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("다른 유저의 상품 상세 정보를 조회하면 예외가 발생한다.")
    void orderDetailExceptionWhenOtherMemberOrder() {
        // given
        // member -> data.sql
        LoginResponse loginResponse = post(
            "login",
            new LoginRequest("hello2@woowa.com", "hello2")
        ).body().as(LoginResponse.class);
        ProductHelper.createProduct(new ProductCreationRequest("피자", "10000", "imageUrl"));
        CartHelper.createCartItem(new CartItemCreationRequest(1L));
        post("/api/orders", new OrderCreationRequest(List.of(1L)), loginResponse.getAccessToken());

        // when
        ExtractableResponse<Response> response = get("/api/orders/{orderId}", 1L, DEFAULT_TOKEN);

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("유저의 주문 목록을 조회한다.")
    void getOrders() {
        // given
        // member -> data.sql
        ProductHelper.createProduct(new ProductCreationRequest("피자", "10000", "imageUrl"));
        CartHelper.createCartItem(new CartItemCreationRequest(1L));
        post("/api/orders", new OrderCreationRequest(List.of(1L)), DEFAULT_TOKEN);

        // when
        ExtractableResponse<Response> response = get("/api/orders", DEFAULT_TOKEN);

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
