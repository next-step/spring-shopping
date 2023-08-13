package shopping.app.accept;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static shopping.app.accept.UrlHelper.Auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.app.accept.UrlHelper.Order;
import shopping.auth.dto.LoginRequest;
import shopping.auth.dto.TokenResponse;
import shopping.core.advice.ErrorTemplate;
import shopping.mart.dto.CartAddRequest;
import shopping.mart.dto.OrderDetailResponse;
import shopping.mart.dto.OrderDetailResponse.OrderedProductResponse;
import shopping.mart.dto.ProductResponse;

@DisplayName("Order 인수테스트")
class OrderAcceptanceTest extends AcceptanceTest {

    private String accessToken;

    @BeforeEach
    void setUpAdmin() {
        LoginRequest adminRequest = new LoginRequest("admin@hello.world", "admin!123");
        this.accessToken = Auth.login(adminRequest).as(TokenResponse.class)
                .getAccessToken();
    }

    @Test
    @DisplayName("POST /orders API는 장바구니에 담긴 물건을 구매 처리하고 Location을 반환한다.")
    void order_and_return_location_when_order() {
        // given
        ProductResponse productResponse = findAllProducts().get(0);
        CartAddRequest request = new CartAddRequest(productResponse.getId());
        UrlHelper.Cart.addProduct(request, accessToken);

        // when
        ExtractableResponse<Response> response = UrlHelper.Order.orderCart(accessToken);

        // then
        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        assertNotNull(response.header("Location"));
    }

    @Test
    @DisplayName("POST /orders API는 장바구니가 비어 있을 경우 ORDER-401 예외를 던진다.")
    void throw_order_401_when_cart_is_empty() {
        // when
        ExtractableResponse<Response> response = UrlHelper.Order.orderCart(accessToken);

        // then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        AssertHelper.Order.assertEmptyCart(response);
    }

    @Test
    @DisplayName("GET /orders/{orderId} API는 orderId에 해당하는 주문 정보를 반환한다.")
    void find_order_detail_by_orderId() {
        // given
        ProductResponse productResponse = findAllProducts().get(0);
        CartAddRequest request = new CartAddRequest(productResponse.getId());
        UrlHelper.Cart.addProduct(request, accessToken);

        ExtractableResponse<Response> orderResponse = UrlHelper.Order.orderCart(accessToken);
        String location = orderResponse.header("Location");

        // when
        OrderDetailResponse result = Order.findOrderDetail(accessToken, location)
                .as(OrderDetailResponse.class);

        // then
        OrderedProductResponse orderedProduct = result.getItems().get(0);
        assertEquals(productResponse.getName(), orderedProduct.getName());
        assertEquals(productResponse.getImageUrl(), orderedProduct.getImageUrl());
        assertEquals(productResponse.getPrice(), orderedProduct.getPrice());
        assertEquals(1, orderedProduct.getCount());
    }

    @Test
    @DisplayName("GET /orders/{orderId} API는 orderId에 해당하는 주문 정보가 없을 경우 ORDER-402를 반환한다.")
    void not_found_order() {
        // when
        ExtractableResponse<Response> response = Order.findOrderDetail(accessToken, "/orders/99999");

        // then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        assertEquals("ORDER-402", response.as(ErrorTemplate.class).getStatusCode());
    }

    @Test
    @DisplayName("GET /orders API는 모든 주문 정보를 반환한다.")
    void find_order_history() {
        // given
        ProductResponse firstProduct = findAllProducts().get(0);
        UrlHelper.Cart.addProduct(new CartAddRequest(firstProduct.getId()), accessToken);
        UrlHelper.Order.orderCart(accessToken);

        ProductResponse secondProduct = findAllProducts().get(1);
        UrlHelper.Cart.addProduct(new CartAddRequest(secondProduct.getId()), accessToken);
        UrlHelper.Order.orderCart(accessToken);

        // when
        ExtractableResponse<Response> response = Order.findOrderHistory(accessToken);

        // then
        List<OrderDetailResponse> result = response.jsonPath().getList(".", OrderDetailResponse.class);
        assertEquals(2, result.size());
    }
}
