package shopping.intergration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static shopping.intergration.helper.RestAssuredHelper.addCartItem;
import static shopping.intergration.helper.RestAssuredHelper.extractObject;
import static shopping.intergration.helper.RestAssuredHelper.login;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.CartItemResponses;
import shopping.dto.response.ProductResponse;

class CartItemIntegrationTest extends IntegrationTest {

    private static final String EMAIL = "yongs170@naver.com";
    private static final String PASSWORD = "123!@#asd";

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
}
