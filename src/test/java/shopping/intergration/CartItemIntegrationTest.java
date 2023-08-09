package shopping.intergration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.CartItemResponses;
import shopping.dto.response.ProductResponse;
import shopping.exception.ErrorCode;
import shopping.repository.CartItemRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static shopping.intergration.helper.CartItemHelper.*;
import static shopping.intergration.helper.LogInHelper.login;
import static shopping.intergration.helper.RestAssuredHelper.extractObject;
import static shopping.intergration.utils.LoginUtils.*;

@DisplayName("장바구니 아이템 테스트")
class CartItemIntegrationTest extends IntegrationTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @AfterEach
    void tearDown() {
        cartItemRepository.deleteAll();
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addProductToCart() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        final Long productId = 1L;

        final ExtractableResponse<Response> response = addCartItemRequest(accessToken, productId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final CartItemResponse cartItemResponse = extractObject(response, CartItemResponse.class);
        final ProductResponse product = cartItemResponse.getProduct();
        assertThat(cartItemResponse.getCartItemId()).isNotNull();
        assertThat(product)
                .extracting("id", "name", "image", "price")
                .contains(productId, "Chicken", "/image/Chicken.png", 10_000);
    }

    @Test
    @DisplayName("장바구니에 상품 목록을 읽는다.")
    void readCartItems() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 2L);

        final ExtractableResponse<Response> response = getCartItemsRequest(accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final CartItemResponses cartItemResponses = extractObject(response, CartItemResponses.class);
        assertThat(cartItemResponses.getCartItems()).hasSize(2)
                .extracting("product.id", "product.name", "quantity")
                .contains(
                        tuple(1L, "Chicken", 2),
                        tuple(2L, "Pizza", 1)
                );
    }

    @Test
    @DisplayName("장바구니의 아이템의 수량을 변경한다.")
    void updateCartItem() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();
        final int updateQuantity = 100;

        final ExtractableResponse<Response> response = updateCartItemRequest(accessToken, targetCartItemId, updateQuantity);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("장바구니의 아이템을 삭제한다.")
    void deleteCartItem() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();

        final ExtractableResponse<Response> response = deleteCartItemRequest(accessToken, targetCartItemId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("장바구니에 상품을 추가할 때 토큰이 없을 때 예외를 던진다.")
    void emptyTokenThenThrow() {
        final Long productId = 1L;

        final ExtractableResponse<Response> response = addCartItemRequestWithoutAccessToken(productId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(ErrorCode.TOKEN_IS_EMPTY.getMessage());
    }

    @Test
    @DisplayName("장바구니에 상품을 추가할 때 토큰이 유효하지 않을 때 예외를 던진다.")
    void invalidTokenThenThrow() {
        final String invalidAccessToken = "invalid";
        final Long productId = 1L;

        final ExtractableResponse<Response> response = addCartItemRequest(invalidAccessToken, productId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(ErrorCode.TOKEN_INVALID.getMessage());
    }

    @Test
    @DisplayName("장바구니의 아이템의 수량을 1000개를 초과하는 경우 예외를 던진다.")
    void moreThanMaxQuantityThenThrow() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();
        final int moreThanMaxQuantity = 1001;

        final ExtractableResponse<Response> response = updateCartItemRequest(accessToken, targetCartItemId, moreThanMaxQuantity);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(ErrorCode.QUANTITY_INVALID.getMessage());
    }

    @Test
    @DisplayName("장바구니의 아이템의 수량을 0개 이하 경우 예외를 던진다.")
    void lessThanMinQuantityThenThrow() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();
        final int lessThanMinQuantity = 0;

        final ExtractableResponse<Response> response = updateCartItemRequest(accessToken, targetCartItemId, lessThanMinQuantity);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(ErrorCode.QUANTITY_INVALID.getMessage());
    }

    @Test
    @DisplayName("다른 회원이 장바구니 아이템을 수정할 때 예외를 던진다.")
    void differentMemberUpdateCartItemThenThrow() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        final String differentMemberAccessToken = login(DIFFERENT_MEMBER_EMAIL, PASSWORD).getToken();
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();
        final int quantity = 3;

        final ExtractableResponse<Response> response = updateCartItemRequest(differentMemberAccessToken, targetCartItemId, quantity);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(
                ErrorCode.FORBIDDEN_MODIFY_CART_ITEM.getMessage());
    }

    @Test
    @DisplayName("다른 회원이 장바구니 아이템을 삭제할 때 예외를 던진다.")
    void differentMemberDeleteCartItemThenThrow() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        final String differentMemberAccessToken = login(DIFFERENT_MEMBER_EMAIL, PASSWORD).getToken();
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();

        final ExtractableResponse<Response> response = deleteCartItemRequest(differentMemberAccessToken, targetCartItemId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(
                ErrorCode.FORBIDDEN_MODIFY_CART_ITEM.getMessage());
    }
}
