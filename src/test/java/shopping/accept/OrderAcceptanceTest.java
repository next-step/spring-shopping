package shopping.accept;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.auth.app.api.request.LoginRequest;
import shopping.auth.app.api.response.TokenResponse;
import shopping.mart.app.api.cart.request.CartAddRequest;
import shopping.mart.app.api.cart.response.CartResponse;
import shopping.mart.app.api.product.response.ProductResponse;
import shopping.order.app.api.receipt.response.ReceiptProductResponse;
import shopping.order.app.api.receipt.response.ReceiptResponse;

@DisplayName("order 인수테스트")
class OrderAcceptanceTest extends AcceptanceTest {

    private String accessToken;

    @BeforeEach
    void setUpAdmin() {
        LoginRequest adminRequest = new LoginRequest("admin@hello.world", "admin!123");
        this.accessToken = UrlHelper.Auth.login(adminRequest).as(TokenResponse.class)
                .getAccessToken();
    }

    @Test
    @DisplayName("POST /orders API는 cart에 담긴 상품을 구매한다.")
    void order_cart_products() {
        // given
        ProductResponse productResponse = findAllProducts().get(0);
        CartAddRequest cartAddRequest = new CartAddRequest(productResponse.getId());

        UrlHelper.Cart.addProduct(cartAddRequest, accessToken);

        CartResponse cartResponse = UrlHelper.Cart.findCart(accessToken).as(CartResponse.class);

        // when
        ExtractableResponse<Response> result = UrlHelper.Order.orderCart(accessToken);

        // then
        AssertHelper.Order.assertOrdered(result);
    }

    @Test
    @DisplayName("POST /orders API는 cartId를 찾을 수 없을경우, Bad Request를 던진다.")
    void throw_Bad_Request_when_cart_does_not_exists() {
        // when
        ExtractableResponse<Response> result = UrlHelper.Order.orderCart(accessToken);

        // then
        AssertHelper.Http.assertIsBadRequest(result);
    }

    @Test
    @DisplayName("GET /receipts API는 로그인된 유저가 구매한 모든 receipts를 반환한다.")
    void return_all_receipts_bought_user() {
        // given
        ProductResponse productResponse = findAllProducts().get(0);
        CartAddRequest cartAddRequest = new CartAddRequest(productResponse.getId());

        UrlHelper.Cart.addProduct(cartAddRequest, accessToken);

        UrlHelper.Order.orderCart(accessToken);

        ReceiptProductResponse receiptProductResponse = new ReceiptProductResponse(productResponse.getId(),
                productResponse.getName(), productResponse.getImageUrl(), productResponse.getPrice(), 1);

        ReceiptResponse expected = new ReceiptResponse(null, List.of(receiptProductResponse));

        // when
        ExtractableResponse<Response> result = UrlHelper.Receipt.history(accessToken);

        // then
        AssertHelper.Receipt.assertReceipt(result, List.of(expected));
    }

    @Test
    @DisplayName("GET /receipts API는 어떠한 구매내역도 찾을 수 없는경우, 빈 배열을 반환한다.")
    void return_empty_array_when_user_bought_any_products() {
        // given
        List<ReceiptResponse> expected = List.of();

        // when
        ExtractableResponse<Response> result = UrlHelper.Receipt.history(accessToken);

        // then
        AssertHelper.Receipt.assertReceipt(result, expected);
    }
}
