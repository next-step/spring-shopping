package integration;

import static org.assertj.core.api.Assertions.assertThat;

import integration.helper.CartHelper;
import integration.helper.OrderHelper;
import integration.helper.ProductHelper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.cart.dto.response.AllCartItemsResponse;
import shopping.order.dto.response.OrderResponse;
import shopping.product.dto.request.ProductCreationRequest;

@DisplayName("주문 통합 테스트")
class OrderIntegrationTest extends IntegrationTest{

    @Test
    @DisplayName("장바구니에 담긴 상품을 주문하면 주문이 등록되고 장바구니가 비워진다")
    void order() {
        ProductCreationRequest productCreationRequest = ProductHelper.createProductCreationRequest();
        Long productId = ProductHelper.createProductAndGetId(productCreationRequest);
        CartHelper.addCartItem(productId);

        ExtractableResponse<Response> response = OrderHelper.addOrder();

        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(CartHelper.getAllCartItems())
                .extracting(AllCartItemsResponse::getCartItemResponses).asList().isEmpty();
    }

    @Test
    @DisplayName("장바구니에 아이템이 없는 경우 주문이 실패한다")
    void orderWithEmptyCart() {
        // when
        ExtractableResponse<Response> response = OrderHelper.addOrder();

        // then
        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @DisplayName("상세 주문을 조회한다")
    void getOrder() {
        Long orderId = createProductAndAddOrder(ProductHelper.createProductCreationRequest());

        ExtractableResponse<Response> response = OrderHelper.getOrder(orderId);

        assertThat(response.statusCode()).isEqualTo(200);
        OrderResponse orderResponse = response.body().as(OrderResponse.class);
        assertThat(orderResponse).extracting(OrderResponse::getId).isEqualTo(orderId);
        assertThat(orderResponse).extracting(OrderResponse::getTotalPrice).isEqualTo(10000);
        double epsilon = 0.000001d;
        Assertions.assertEquals(1000, orderResponse.getExchangeRate(), epsilon);
    }

    @Test
    @DisplayName("모든 주문 목록을 조회한다")
    void getAllOrders() {
        Long orderId = createProductAndAddOrder(ProductHelper.createProductCreationRequest());
        Long orderId2 = createProductAndAddOrder(ProductHelper.createProductCreationRequest2());

        ExtractableResponse<Response> response =  OrderHelper.getAllOrders();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().as(ArrayList.class)).hasSize(2);
    }

    private static Long createProductAndAddOrder(ProductCreationRequest productCreationRequest) {
        Long productId = ProductHelper.createProductAndGetId(productCreationRequest);
        CartHelper.addCartItem(productId);
        return OrderHelper.addOrderAndGetId();
    }
}
