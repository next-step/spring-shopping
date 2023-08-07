package shopping.intergration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static shopping.exception.ShoppingErrorType.FORBIDDEN_MODIFY_CART_ITEM;
import static shopping.exception.ShoppingErrorType.QUANTITY_INVALID;
import static shopping.exception.ShoppingErrorType.TOKEN_INVALID;
import static shopping.exception.ShoppingErrorType.TOKEN_IS_EMPTY;
import static shopping.intergration.helper.RestAssuredHelper.addCartItem;
import static shopping.intergration.helper.RestAssuredHelper.extractObject;
import static shopping.intergration.helper.RestAssuredHelper.login;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.CartItemResponses;
import shopping.dto.response.ProductResponse;
import shopping.repository.CartItemRepository;

class CartItemIntegrationTest extends IntegrationTest {

    private static final String EMAIL = "yongs170@naver.com";
    private static final String PASSWORD = "123!@#asd";
    private static final String DIFFERENT_MEMBER_EMAIL = "proto_seo@naver.com";

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

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartItemAddRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart-items")
                .then().log().all().extract();

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

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart-items")
                .then().log().all().extract();

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

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartItemUpdateRequest(updateQuantity))
                .when().patch("/cart-items/{cartItemId}", targetCartItemId)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final CartItemResponse cartItemResponse = extractObject(response, CartItemResponse.class);
        assertThat(cartItemResponse.getQuantity()).isEqualTo(updateQuantity);
    }

    @Test
    @DisplayName("장바구니의 아이템을 삭제한다.")
    void deleteCartItem() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart-items/{cartItemId}", targetCartItemId)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니에 상품을 추가할 때 토큰이 없을 때 예외를 던진다.")
    void emptyTokenThenThrow() {
        final Long productId = 1L;

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().none()
                .body(new CartItemAddRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart-items")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(TOKEN_IS_EMPTY.getMessage());
    }

    @Test
    @DisplayName("장바구니에 상품을 추가할 때 토큰이 유효하지 않을 때 예외를 던진다.")
    void invalidTokenThenThrow() {
        final String invalidAccessToken = "invalid";
        final Long productId = 1L;

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(invalidAccessToken)
                .body(new CartItemAddRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart-items")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(TOKEN_INVALID.getMessage());
    }

    @Test
    @DisplayName("장바구니의 아이템의 수량을 1000개를 초과하는 경우 예외를 던진다.")
    void moreThanMaxQuantityThenThrow() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();
        final int moreThanMaxQuantity = 1001;

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartItemUpdateRequest(moreThanMaxQuantity))
                .when().patch("/cart-items/{cartItemId}", targetCartItemId)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(QUANTITY_INVALID.getMessage());
    }

    @Test
    @DisplayName("장바구니의 아이템의 수량을 0개 이하 경우 예외를 던진다.")
    void lessThanMinQuantityThenThrow() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        addCartItem(accessToken, 1L);
        addCartItem(accessToken, 1L);
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();
        final int lessThanMinQuantity = 0;

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartItemUpdateRequest(lessThanMinQuantity))
                .when().patch("/cart-items/{cartItemId}", targetCartItemId)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(QUANTITY_INVALID.getMessage());
    }

    @Test
    @DisplayName("다른 회원이 장바구니 아이템을 수정할 때 예외를 던진다.")
    void differentMemberUpdateCartItemThenThrow() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        final String differentMemberAccessToken = login(DIFFERENT_MEMBER_EMAIL, PASSWORD).getToken();
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();
        final int quantity = 3;

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(differentMemberAccessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartItemUpdateRequest(quantity))
                .when().patch("/cart-items/{cartItemId}", targetCartItemId)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(
                FORBIDDEN_MODIFY_CART_ITEM.getMessage());
    }

    @Test
    @DisplayName("다른 회원이 장바구니 아이템을 삭제할 때 예외를 던진다.")
    void differentMemberDeleteCartItemThenThrow() {
        final String accessToken = login(EMAIL, PASSWORD).getToken();
        final String differentMemberAccessToken = login(DIFFERENT_MEMBER_EMAIL, PASSWORD).getToken();
        final Long targetCartItemId = addCartItem(accessToken, 2L).getCartItemId();

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(differentMemberAccessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart-items/{cartItemId}", targetCartItemId)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo(
                FORBIDDEN_MODIFY_CART_ITEM.getMessage());
    }
}
