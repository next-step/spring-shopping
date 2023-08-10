package integration;

import static integration.helper.CommonRestAssuredUtils.DEFAULT_TOKEN;
import static integration.helper.CommonRestAssuredUtils.delete;
import static integration.helper.CommonRestAssuredUtils.get;
import static integration.helper.CommonRestAssuredUtils.patch;
import static integration.helper.CommonRestAssuredUtils.post;
import static org.assertj.core.api.Assertions.assertThat;

import integration.helper.ProductHelper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.cart.dto.request.CartItemCreationRequest;
import shopping.cart.dto.request.CartItemQuantityUpdateRequest;
import shopping.cart.dto.response.AllCartItemsResponse;
import shopping.cart.dto.response.CartItemResponse;
import shopping.product.dto.request.ProductCreationRequest;

class CartIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void create() {
        // given
        // member -> data.sql
        ProductHelper.createProduct(new ProductCreationRequest("피자", "10000", "imageUrl"));

        //when
        ExtractableResponse<Response> response = post("/api/carts", new CartItemCreationRequest(1L),
            DEFAULT_TOKEN);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("장바구니에 상품 리스트를 조회한다.")
    void readAll() {
        // given
        // member -> data.sql
        ProductHelper.createProduct(new ProductCreationRequest("피자", "10000", "imageUrl"));
        ProductHelper.createProduct(new ProductCreationRequest("치킨", "100000", "imageUrl1"));
        ProductHelper.createProduct(new ProductCreationRequest("햄버거", "1000", "imageUrl2"));

        post("/api/carts", new CartItemCreationRequest(1L), DEFAULT_TOKEN);
        post("/api/carts", new CartItemCreationRequest(2L), DEFAULT_TOKEN);
        post("/api/carts", new CartItemCreationRequest(3L), DEFAULT_TOKEN);

        //when
        ExtractableResponse<Response> response = get("/api/carts", DEFAULT_TOKEN);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        AllCartItemsResponse allCartItem = response.body().as(AllCartItemsResponse.class);

        assertThat(allCartItem).extracting(AllCartItemsResponse::isChanged)
            .isEqualTo(false);
        assertThat(allCartItem).extracting(AllCartItemsResponse::getCartItemResponses)
            .extracting(List::size)
            .isEqualTo(3);
        assertThat(allCartItem).extracting(AllCartItemsResponse::getChangedCartItemResponses)
            .extracting(List::size)
            .isEqualTo(0);

    }

    @Test
    @DisplayName("상품 개수를 수정한다.")
    void updateQuantity() {
        ProductHelper.createProduct(new ProductCreationRequest("피자", "10000", "imageUrl"));
        post("/api/carts", new CartItemCreationRequest(1L), DEFAULT_TOKEN);

        ExtractableResponse<Response> response = patch("/api/carts/{cartItemId}/quantity", 1L,
            new CartItemQuantityUpdateRequest(2), DEFAULT_TOKEN);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        CartItemResponse targetItem = get("/api/carts", DEFAULT_TOKEN).body()
            .as(AllCartItemsResponse.class).getCartItemResponses()
            .get(0);
        assertThat(targetItem).extracting("quantity").isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 물품을 삭제한다.")
    void deleteCartItem() {
        ProductHelper.createProduct(new ProductCreationRequest("피자", "10000", "imageUrl"));
        post("/api/carts", new CartItemCreationRequest(1L), DEFAULT_TOKEN);

        ExtractableResponse<Response> response = delete("/api/carts/{cartItemId}", 1L, DEFAULT_TOKEN);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<CartItemResponse> cartItemResponses = get("/api/carts", DEFAULT_TOKEN).body()
            .as(AllCartItemsResponse.class).getCartItemResponses();
        assertThat(cartItemResponses).isEmpty();
    }
}
