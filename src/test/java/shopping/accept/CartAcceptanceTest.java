package shopping.accept;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.accept.AssertHelper.Http;
import shopping.accept.UrlHelper.Cart;
import shopping.auth.domain.usecase.request.LoginRequest;
import shopping.auth.domain.usecase.response.TokenResponse;
import shopping.mart.domain.usecase.cart.request.CartAddRequest;
import shopping.mart.domain.usecase.cart.request.CartUpdateRequest;
import shopping.mart.domain.usecase.cart.response.CartResponse;
import shopping.mart.domain.usecase.product.response.ProductResponse;

@DisplayName("Cart 인수테스트")
class CartAcceptanceTest extends AcceptanceTest {

    private String accessToken;

    @BeforeEach
    void setUpAdmin() {
        LoginRequest adminRequest = new LoginRequest("admin@hello.world", "admin!123");
        this.accessToken = UrlHelper.Auth.login(adminRequest).as(TokenResponse.class)
            .getAccessToken();
    }

    @Test
    @DisplayName("POST /carts API는 product를 cart에 추가한다.")
    void add_product_to_cart() {
        // given
        ProductResponse productResponse = findAllProducts().get(0);
        CartAddRequest request = new CartAddRequest(productResponse.getId());

        // when
        ExtractableResponse<Response> result = Cart.addProduct(request, accessToken);

        // then
        Http.assertIsCreated(result);
    }

    @Test
    @DisplayName("POST /carts API는 인증에 실패하면, UnAuthorization 을 던진다.")
    void throw_UnAuthorization_when_auth_failed() {
        // given
        ProductResponse productResponse = findAllProducts().get(0);
        CartAddRequest request = new CartAddRequest(productResponse.getId());

        String invalidToken = "123";

        // when
        ExtractableResponse<Response> result = Cart.addProduct(request, invalidToken);

        // then
        Http.assertIsUnAuthorization(result);
    }

    @Test
    @DisplayName("POST /carts API는 product를 찾을 수 없으면, Bad Request 을 던진다.")
    void throw_Bad_Request_when_cart_does_not_exist() {
        // given
        CartAddRequest request = new CartAddRequest(9999999L);

        // when
        ExtractableResponse<Response> result = Cart.addProduct(request, accessToken);

        // then
        Http.assertIsBadRequest(result);
    }

    @Test
    @DisplayName("GET /carts API는 cart에 있는 모든 product를 반환한다.")
    void find_all_products_in_cart() {
        // given
        List<ProductResponse> allProducts = findAllProducts();
        allProducts.forEach(
            product -> Cart.addProduct(new CartAddRequest(product.getId()), accessToken));
        CartResponse exactlyExpected = getExpectedCartResponse(allProducts);

        // when
        ExtractableResponse<Response> result = Cart.findCart(accessToken);

        // then
        AssertHelper.Cart.assertCart(result, exactlyExpected);
    }

    @Test
    @DisplayName("PATCH /carts API는 cart에 있는 한 product의 수량을 변경한다.")
    void update_product_count() {
        // given
        List<ProductResponse> allProducts = findAllProducts();
        allProducts.forEach(
            product -> Cart.addProduct(new CartAddRequest(product.getId()), accessToken));
        ProductResponse updateTarget = allProducts.get(0);
        int updateCount = 100_000;

        CartUpdateRequest request = new CartUpdateRequest(updateTarget.getId(), updateCount);

        // when
        Cart.updateCart(request, accessToken);
        ExtractableResponse<Response> findResult = Cart.findCart(accessToken);

        // then
        AssertHelper.Cart.assertCartUpdated(findResult, updateTarget.getId(), updateCount);
    }

    @Test
    @DisplayName("PATCH /carts API는 업데이트할 product가 없을경우, Bad Request 를 던진다.")
    void throw_Bad_Request_when_does_not_exist_product() {
        // given
        CartUpdateRequest request = new CartUpdateRequest(999999999L, 100_000);

        // when
        ExtractableResponse<Response> result = Cart.updateCart(request, accessToken);

        // then
        Http.assertIsBadRequest(result);
    }

    @Test
    @DisplayName("PATCH /carts API는 count가 0이하일경우, Bad Request 를 던진다.")
    void throw_Bad_Request_when_product_count_zero() {
        // given
        List<ProductResponse> allProducts = findAllProducts();
        allProducts.forEach(
            product -> Cart.addProduct(new CartAddRequest(product.getId()), accessToken));
        ProductResponse updateTarget = allProducts.get(0);
        int updateCount = 0;

        CartUpdateRequest request = new CartUpdateRequest(updateTarget.getId(), updateCount);

        // when
        ExtractableResponse<Response> result = Cart.updateCart(request, accessToken);

        // then
        Http.assertIsBadRequest(result);
    }

    @Test
    @DisplayName("DELETE /carts?product-id={productId} API는 cart에 있는 product를 삭제한다.")
    void delete_product() {
        // given
        List<ProductResponse> allProducts = findAllProducts();
        allProducts.forEach(
            product -> Cart.addProduct(new CartAddRequest(product.getId()), accessToken));
        long deleteTargetId = allProducts.get(0).getId();

        // when
        ExtractableResponse<Response> result = Cart.deleteCart(deleteTargetId, accessToken);

        // then
        Http.assertIsNoContent(result);
    }

    @Test
    @DisplayName("DELETE /carts?product-id={productId} API는 productId에 해당하는 product가 없을경우, Bad Request 을 던진다.")
    void throw_Bad_Request_when_does_not_exist_product_on_delete() {
        // given
        long deleteTargetId = 999999999L;

        // when
        ExtractableResponse<Response> result = Cart.deleteCart(deleteTargetId, accessToken);

        // then
        Http.assertIsBadRequest(result);
    }

    private CartResponse getExpectedCartResponse(List<ProductResponse> productResponses) {
        return new CartResponse(1L, productResponses.stream().map(
            productResponse -> new CartResponse.ProductResponse(
                    productResponse.getId(), 1, productResponse.getImageUrl(),
                    productResponse.getName(), productResponse.getPrice())
        ).collect(Collectors.toList()));
    }

}
