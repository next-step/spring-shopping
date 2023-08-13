package shopping.app.accept;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static shopping.app.accept.UrlHelper.Auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.auth.dto.LoginRequest;
import shopping.auth.dto.TokenResponse;
import shopping.mart.dto.CartAddRequest;
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
}
