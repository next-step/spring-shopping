package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.acceptance.helper.AuthHelper;
import shopping.acceptance.helper.CartProductHelper;
import shopping.dto.request.CartProductQuantityUpdateRequest;
import shopping.dto.request.CartProductRequest;
import shopping.dto.response.CartResponse;
import shopping.exception.dto.ExceptionResponse;

@DisplayName("장바구니 상품 관련 기능 인수 테스트")
class CartProductAcceptanceTest extends AcceptanceTest {

    // TODO: 언해피케이스
    @Test
    @DisplayName("장바구니 상품 관련 기능은 인증되지 않으면 사용할 수 없다.")
    void checkAuthentication() {
        /* given */
        final String jwt = "abcd";
        final CartProductRequest request = new CartProductRequest(3L);

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .body(request)
            .when().post("/api/cart")
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("장바구니 상품을 추가한다.")
    void createCartProduct() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        final CartProductRequest request = new CartProductRequest(3L);

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .body(request)
            .when().post("/api/cart")
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품이 존재하지 않는 경우 \"존재하지 않는 상품입니다.\"로 응답한다.")
    void createCartProductBadRequestWithDoesNotExistProduct() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        final CartProductRequest request = new CartProductRequest(123L);

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .body(request)
            .when().post("/api/cart")
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().jsonPath().getString("message")).isEqualTo(
            "존재하지 않는 상품입니다. 입력값: 123");
    }

    @Test
    @DisplayName("장바구니 상품이 이미 존재하는 경우 \"이미 장바구니에 담긴 상품입니다.\"로 응답한다.")
    void createCartProductBadRequestWithExistCartProduct() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        final CartProductRequest request = new CartProductRequest(3L);
        CartProductHelper.createCartProduct(jwt, request);

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .body(request)
            .when().post("/api/cart")
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().jsonPath().getString("message"))
            .isEqualTo("이미 장바구니에 담긴 상품입니다. 입력값: 3");
    }


    @Test
    @DisplayName("장바구니 상품을 모두 조회한다.")
    void getAllCartProducts() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        CartProductHelper.createCartProduct(jwt, new CartProductRequest(3L));

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .when().get("/api/cart")
            .then().log().all()
            .extract();

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
        CartProductHelper.createCartProduct(jwt, new CartProductRequest(3L));

        final Long cartProductId = 1L;
        final CartProductQuantityUpdateRequest cartProductQuantityUpdateRequest =
            new CartProductQuantityUpdateRequest(2);

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .body(cartProductQuantityUpdateRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .when().patch("/api/cart/{cartProductId}", cartProductId)
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니 상품의 수량을 0 이하로 수정할 경우 Bad Request으로 응답한다.")
    void updateFailWithCartProductQuantityZero() {
        /* given */
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        CartProductHelper.createCartProduct(jwt, new CartProductRequest(3L));

        final Long cartProductId = 1L;
        final CartProductQuantityUpdateRequest cartProductQuantityUpdateRequest =
            new CartProductQuantityUpdateRequest(0);

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .body(cartProductQuantityUpdateRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .when().patch("/api/cart/{cartProductId}", cartProductId)
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().as(ExceptionResponse.class).getMessage())
            .isEqualTo("장바구니 상품 개수는 0이하면 안됩니다. 입력값: 0");
    }
}
