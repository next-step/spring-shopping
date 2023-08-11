package shopping.accept;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import shopping.auth.domain.usecase.response.TokenResponse;
import shopping.mart.domain.usecase.cart.response.CartResponse;
import shopping.mart.domain.usecase.cart.response.CartResponse.ProductResponse;
import shopping.order.domain.usecase.receipt.response.ReceiptDetailProductResponse;
import shopping.order.domain.usecase.receipt.response.ReceiptDetailResponse;
import shopping.order.domain.usecase.receipt.response.ReceiptProductResponse;
import shopping.order.domain.usecase.receipt.response.ReceiptResponse;

class AssertHelper {

    static final class Product {

        static void assertAllProducts(final ValidatableResponse response,
                String... exactlyExpected) {
            response.statusCode(HttpStatus.SC_OK);
            Arrays.stream(exactlyExpected)
                    .forEach(expected -> response.body(Matchers.containsString(expected)));
        }
    }

    static final class Auth {

        static void assertJwt(final ExtractableResponse<Response> result) {
            assertThat(result.statusCode()).isEqualTo(HttpStatus.SC_OK);
            assertThat(result.body().as(TokenResponse.class).getAccessToken()).isNotNull();
        }
    }

    static final class Cart {

        static void assertCart(ExtractableResponse<Response> result,
                CartResponse exactlyExpected) {
            Http.assertIsOk(result);

            assertThat(result.as(CartResponse.class).getProductResponses()).isEqualTo(
                    exactlyExpected.getProductResponses());
        }

        static void assertCartUpdated(ExtractableResponse<Response> findResult, long id,
                int expectedCount) {
            Http.assertIsOk(findResult);
            CartResponse cartResponse = findResult.as(CartResponse.class);
            Optional<ProductResponse> optionalProductResponse = cartResponse.getProductResponses()
                    .stream()
                    .filter(productResponse -> productResponse.getId() == id)
                    .findFirst();

            assertThat(optionalProductResponse).isPresent();
            assertThat(optionalProductResponse.get().getCount()).isEqualTo(expectedCount);
        }
    }

    static final class Order {

        static void assertOrdered(ExtractableResponse<Response> result) {
            Http.assertIsOk(result);

            assertThat(result.header(HttpHeaders.LOCATION)).contains("/order-detail");
        }
    }

    static final class Receipt {

        static void assertReceipt(ExtractableResponse<Response> result, List<ReceiptResponse> expected) {
            Http.assertIsOk(result);

            List<ReceiptResponse> resultElement = result.as(new TypeRef<>() {
            });
            assertReceiptResponses(resultElement, expected);
        }

        private static void assertReceiptResponses(List<ReceiptResponse> result,
                List<ReceiptResponse> expected) {

            assertThat(result).hasSize(expected.size());

            SoftAssertions.assertSoftly(softAssertions -> {
                for (int i = 0; i < result.size(); i++) {
                    assertExactlyReceiptProductResponse(result.get(i).getReceiptProducts(),
                            expected.get(i).getReceiptProducts());
                }
            });
        }

        private static void assertExactlyReceiptProductResponse(List<ReceiptProductResponse> result,
                List<ReceiptProductResponse> expected) {

            SoftAssertions.assertSoftly(softAssertions -> {
                assertThat(result).hasSize(expected.size());
                for (int i = 0; i < result.size(); i++) {
                    ReceiptProductResponse resultElement = result.get(i);
                    ReceiptProductResponse expectedElement = result.get(i);
                    softAssertions.assertThat(resultElement.getName()).isEqualTo(expectedElement.getName());
                    softAssertions.assertThat(resultElement.getPrice()).isEqualTo(expectedElement.getPrice());
                    softAssertions.assertThat(resultElement.getImageUrl()).isEqualTo(expectedElement.getImageUrl());
                    softAssertions.assertThat(resultElement.getQuantity()).isEqualTo(expectedElement.getQuantity());
                }
            });
        }

        static void assertReceiptDetail(ExtractableResponse<Response> result, ReceiptDetailResponse expected) {

            ReceiptDetailResponse resultElement = result.as(ReceiptDetailResponse.class);
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(resultElement.getId()).isEqualTo(expected.getId());
                softAssertions.assertThat(resultElement.getTotalPrice()).isEqualTo(expected.getTotalPrice());
                softAssertions.assertThat(resultElement.getExchangedPrice()).isNotNull();
                softAssertions.assertThat(resultElement.getExchangeRate()).isNotNull();
                assertExactlyReceiptDetailProduct(resultElement.getReceiptDetailProducts(), expected.getReceiptDetailProducts());
            });
        }

        private static void assertExactlyReceiptDetailProduct(List<ReceiptDetailProductResponse> result,
            List<ReceiptDetailProductResponse> expected) {

            SoftAssertions.assertSoftly(softAssertions -> {
                assertThat(result).hasSize(expected.size());
                for (int i = 0; i < result.size(); i++) {
                    ReceiptDetailProductResponse resultElement = result.get(i);
                    ReceiptDetailProductResponse expectedElement = result.get(i);
                    softAssertions.assertThat(resultElement.getName()).isEqualTo(expectedElement.getName());
                    softAssertions.assertThat(resultElement.getPrice()).isEqualTo(expectedElement.getPrice());
                    softAssertions.assertThat(resultElement.getImageUrl()).isEqualTo(expectedElement.getImageUrl());
                    softAssertions.assertThat(resultElement.getQuantity()).isEqualTo(expectedElement.getQuantity());
                }
            });
        }
    }

    static final class Http {

        static void assertIsOk(final ExtractableResponse<Response> result) {
            assertThat(result.statusCode()).isEqualTo(HttpStatus.SC_OK);
        }

        static void assertIsCreated(final ExtractableResponse<Response> result) {
            assertThat(result.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
        }

        static void assertIsNoContent(final ExtractableResponse<Response> result) {
            assertThat(result.statusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
        }

        public static void assertIsBadRequest(ExtractableResponse<Response> result) {
            assertThat(result.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        }

        public static void assertIsUnAuthorization(ExtractableResponse<Response> result) {
            assertThat(result.statusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
        }
    }
}
