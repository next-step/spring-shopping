package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.acceptance.helper.AuthHelper;
import shopping.acceptance.helper.CartProductHelper;
import shopping.acceptance.helper.RestHelper;
import shopping.dto.request.CartProductCreateRequest;
import shopping.dto.request.CartProductQuantityUpdateRequest;
import shopping.dto.response.CartResponse;
import shopping.exception.dto.ExceptionResponse;

@DisplayName("장바구니 상품 관련 기능 인수 테스트")
class CartProductAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("장바구니 상품 관련 기능은 인증되지 않으면 사용할 수 없다.")
    void checkAuthentication() {
        /* given */
        final String jwt = "abcd";
        final CartProductCreateRequest request = new CartProductCreateRequest(3L);

        /* when */
        final ExtractableResponse<Response> response = RestHelper.post("/api/cartProduct", jwt,
            request);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("장바구니 상품을 추가한다.")
    void createCartProduct() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        final CartProductCreateRequest request = new CartProductCreateRequest(3L);

        /* when */
        final ExtractableResponse<Response> response = RestHelper.post("/api/cartProduct", jwt,
            request);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품이 존재하지 않는 경우 \"존재하지 않는 상품입니다.\"로 응답한다.")
    void createCartProductBadRequestWithDoesNotExistProduct() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        final CartProductCreateRequest request = new CartProductCreateRequest(123L);

        /* when */
        final ExtractableResponse<Response> response = RestHelper.post("/api/cartProduct", jwt,
            request);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.body().jsonPath().getString("message")).isEqualTo(
            "존재하지 않는 상품입니다.");
    }

    @Test
    @DisplayName("장바구니 상품이 이미 존재하는 경우 \"이미 장바구니에 담긴 상품입니다.\"로 응답한다.")
    void createCartProductBadRequestWithExistCartProduct() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        CartProductHelper.createCartProduct(jwt, 3L);

        /* when */
        final ExtractableResponse<Response> response = CartProductHelper.createCartProduct(jwt, 3L);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().jsonPath().getString("message"))
            .isEqualTo("이미 장바구니에 담긴 상품입니다.");
    }


    @Test
    @DisplayName("장바구니 상품을 모두 조회한다.")
    void getAllCartProducts() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        CartProductHelper.createCartProduct(jwt, 3L);

        /* when */
        final ExtractableResponse<Response> response = RestHelper.get("/api/cartProduct", jwt);

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(new TypeRef<List<CartResponse>>() {
        })).hasSize(3);
    }

    @Test
    @DisplayName("장바구니 상품의 수량을 수정한다.")
    void updateCartProductQuantity() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        CartProductHelper.createCartProduct(jwt, 3L);

        final Long cartProductId = 1L;
        final CartProductQuantityUpdateRequest request = new CartProductQuantityUpdateRequest(2);

        /* when */
        final ExtractableResponse<Response> response = RestHelper
            .patch("/api/cartProduct/{cartProductId}", jwt, request, List.of(cartProductId));

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니 상품의 수량을 0 미만으로 수정할 경우 Bad Request으로 응답한다.")
    void updateFailWithCartProductQuantityZero() {
        /* given */
        final int quantity = -1;
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        CartProductHelper.createCartProduct(jwt, 3L);

        final Long cartProductId = 1L;
        final CartProductQuantityUpdateRequest request =
            new CartProductQuantityUpdateRequest(quantity);

        /* when */
        final ExtractableResponse<Response> response = RestHelper
            .patch("/api/cartProduct/{cartProductId}", jwt, request, List.of(cartProductId));

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().as(ExceptionResponse.class).getMessage())
            .isEqualTo("장바구니 상품 개수는 0 이하일 수 없습니다.");
    }


    @Test
    @DisplayName("장바구니 상품을 삭제한다.")
    void deleteCartProduct() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        CartProductHelper.createCartProduct(jwt, 3L);

        final Long cartProductId = 3L;

        /* when */
        final ExtractableResponse<Response> response = RestHelper
            .delete("/api/cartProduct/{cartProductId}", jwt, List.of(cartProductId));

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("장바구니 상품 개수가 0개인 경우 장바구니 상품이 제거된다.")
    void updateCartProductQuantityWithZeroQuantity() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        CartProductHelper.createCartProduct(jwt, 3L);

        final Long cartProductId = 1L;
        final CartProductQuantityUpdateRequest request = new CartProductQuantityUpdateRequest(0);

        /* when */
        final ExtractableResponse<Response> response = RestHelper
            .patch("/api/cartProduct/{cartProductId}", jwt, request, List.of(cartProductId));

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(
            RestHelper
                .get("/api/cartProduct", jwt).body().as(new TypeRef<List<CartResponse>>() {
                })).hasSize(2);
    }

    @Test
    @DisplayName("장바구니 담긴 상품이 존재하지 않는 경우, \"존재하지 않는 장바구니 상품입니다.\", Bad Request로 응답한다.")
    void updateCartProductQuantityWithNotFoundCartProduct() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        CartProductHelper.createCartProduct(jwt, 3L);

        final Long cartProductId = 1234L;
        final CartProductQuantityUpdateRequest request = new CartProductQuantityUpdateRequest(2);

        /* when */
        final ExtractableResponse<Response> response = RestHelper
            .patch("/api/cartProduct/{cartProductId}", jwt, request, List.of(cartProductId));

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.body().as(ExceptionResponse.class).getMessage())
            .isEqualTo("존재하지 않는 장바구니 상품입니다.");
    }
}
