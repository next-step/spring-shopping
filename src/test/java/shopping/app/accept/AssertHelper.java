package shopping.app.accept;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.Arrays;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;
import shopping.auth.dto.TokenResponse;
import shopping.core.advice.ErrorTemplate;
import shopping.mart.dto.CartResponse;
import shopping.mart.dto.CartResponse.ProductResponse;

class AssertHelper {

    static final class Product {

        static void assertAllProducts(final ValidatableResponse response,
                String... exactlyExpected) {
            response.statusCode(HttpStatus.OK.value());
            Arrays.stream(exactlyExpected)
                    .forEach(expected -> response.body(Matchers.containsString(expected)));
        }
    }

    static final class Auth {

        static void assertJwt(final ExtractableResponse<Response> response) {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.body().as(TokenResponse.class).getAccessToken()).isNotNull();
        }

        static void assertLoginFailed(final ExtractableResponse<Response> response) {
            ErrorTemplate errorTemplate = response.as(ErrorTemplate.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(errorTemplate.getStatusCode()).isEqualTo("AUTH-401");
        }

        static void assertTokenDeprecateFailed(final ExtractableResponse<Response> response) {
            ErrorTemplate errorTemplate = response.as(ErrorTemplate.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(errorTemplate.getStatusCode()).isEqualTo("AUTH-INTERCEPTOR-401");
        }
    }

    static final class Cart {

        static void assertProductNotFound(final ExtractableResponse<Response> result) {
            assertThat(result.as(ErrorTemplate.class).getStatusCode()).isEqualTo("CART-SERVICE-401");
        }

        static void assertCart(final ExtractableResponse<Response> result, CartResponse exactlyExpected) {
            Http.assertIsOk(result);

            assertThat(result.as(CartResponse.class).getProductResponses()).containsAll(
                    exactlyExpected.getProductResponses());
        }

        static void assertCartUpdated(ExtractableResponse<Response> findResult, long id, int expectedCount) {
            Http.assertIsOk(findResult);
            CartResponse cartResponse = findResult.as(CartResponse.class);
            Optional<ProductResponse> optionalProductResponse = cartResponse.getProductResponses()
                    .stream()
                    .filter(productResponse -> productResponse.getId() == id)
                    .findFirst();

            assertThat(optionalProductResponse).isPresent();
            assertThat(optionalProductResponse.get().getCount()).isEqualTo(expectedCount);
        }

        static void assertDeleteFailed(ExtractableResponse<Response> result) {
            Http.assertIsBadRequest(result);

            ErrorTemplate errorTemplate = result.as(ErrorTemplate.class);
            assertThat(errorTemplate.getStatusCode()).isEqualTo("CART-SERVICE-401");
        }

        static void assertUpdatableProductNotFound(ExtractableResponse<Response> result) {
            Http.assertIsBadRequest(result);

            ErrorTemplate errorTemplate = result.as(ErrorTemplate.class);
            assertThat(errorTemplate.getStatusCode()).isEqualTo("CART-SERVICE-401");
        }

        static void assertUpdateCountNotPositive(ExtractableResponse<Response> result) {
            Http.assertIsBadRequest(result);

            ErrorTemplate errorTemplate = result.as(ErrorTemplate.class);
            assertThat(errorTemplate.getStatusCode()).isEqualTo("CART-403");
        }
    }

    static final class Http {

        static void assertIsOk(final ExtractableResponse<Response> response) {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        static void assertIsCreated(final ExtractableResponse<Response> response) {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        }

        static void assertIsNoContent(final ExtractableResponse<Response> response) {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        public static void assertIsBadRequest(ExtractableResponse<Response> response) {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }
}
