package shopping.accept;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.auth.domain.usecase.request.LoginRequest;
import shopping.auth.domain.usecase.response.TokenResponse;
import shopping.mart.domain.usecase.cart.request.CartAddRequest;
import shopping.mart.domain.usecase.product.response.ProductResponse;
import shopping.order.domain.usecase.receipt.response.ReceiptDetailProductResponse;
import shopping.order.domain.usecase.receipt.response.ReceiptDetailResponse;
import shopping.order.domain.usecase.receipt.response.ReceiptProductResponse;
import shopping.order.domain.usecase.receipt.response.ReceiptResponse;

@DisplayName("Receipt 인수테스트")
class ReceiptAcceptanceTest extends AcceptanceTest {

    private String accessToken;

    @BeforeEach
    void setUpAdmin() {
        LoginRequest adminRequest = new LoginRequest("admin@hello.world", "admin!123");
        this.accessToken = UrlHelper.Auth.login(adminRequest).as(TokenResponse.class)
                .getAccessToken();
    }

    @Test
    @DisplayName("GET /receipts API는 로그인된 유저가 구매한 모든 receipts를 반환한다.")
    void return_all_receipts_bought_user() {
        // given
        ProductResponse productResponse = findAllProducts().get(0);
        CartAddRequest cartAddRequest = new CartAddRequest(productResponse.getId());

        UrlHelper.Cart.addProduct(cartAddRequest, accessToken);

        UrlHelper.Order.orderCart(accessToken);

        ReceiptProductResponse receiptProductResponse = new ReceiptProductResponse(
                productResponse.getId(),
                productResponse.getName(), productResponse.getImageUrl(), productResponse.getPrice(),
                1);

        ReceiptResponse expected = new ReceiptResponse(null, List.of(receiptProductResponse));

        // when
        ExtractableResponse<Response> result = UrlHelper.Receipt.history(accessToken);

        // then
        AssertHelper.Receipt.assertReceipt(result, List.of(expected));
    }

    @Test
    @DisplayName("GET /receipts API는 어떠한 구매내역도 찾을 수 없는경우, 빈 배열을 반환한다.")
    void return_empty_array_when_user_bought_any_products() {
        // given
        List<ReceiptResponse> expected = List.of();

        // when
        ExtractableResponse<Response> result = UrlHelper.Receipt.history(accessToken);

        // then
        AssertHelper.Receipt.assertReceipt(result, expected);
    }

    @Test
    @DisplayName("GET /receipts/{receipt-id} API는 유저가 구매한 특정 receipt를 반환한다.")
    void return_specific_receipt_bought_user() {
        // given
        ProductResponse productResponse = findAllProducts().get(0);
        CartAddRequest cartAddRequest = new CartAddRequest(productResponse.getId());

        UrlHelper.Cart.addProduct(cartAddRequest, accessToken);

        UrlHelper.Order.orderCart(accessToken);

        List<ReceiptResponse> receiptResponses = UrlHelper.Receipt.history(accessToken)
                .as(new TypeRef<>() {
                });

        ReceiptResponse receiptResponse = receiptResponses.get(0);
        ReceiptDetailResponse expected = fromReceiptResponse(receiptResponse);

        // when
        ExtractableResponse<Response> result = UrlHelper.Receipt.details(receiptResponse.getId(), accessToken);

        // then
        AssertHelper.Receipt.assertReceiptDetail(result, expected);
    }

    @Test
    @DisplayName("GET /receipts/{receipt-id} API는 receipt-id에 해당하는 receipt를 찾을 수 없으면, BadRequest를 던진다.")
    void throw_bad_request_when_cannot_find_receipt() {
        // given
        long receiptId = 1L;

        // when
        ExtractableResponse<Response> result = UrlHelper.Receipt.details(receiptId, accessToken);

        // then
        AssertHelper.Http.assertIsBadRequest(result);
    }

    private ReceiptDetailResponse fromReceiptResponse(ReceiptResponse receiptResponse) {
        List<ReceiptDetailProductResponse> receiptDetailProductResponses = receiptResponse.getReceiptProducts()
                .stream()
                .map(receiptProduct -> new ReceiptDetailProductResponse(receiptProduct.getProductId(),
                        receiptProduct.getName(), receiptProduct.getPrice(), receiptProduct.getImageUrl(),
                        receiptProduct.getQuantity()))
                .collect(Collectors.toList());

        String totalPrice = calculateTotalMoney(receiptDetailProductResponses);

        return new ReceiptDetailResponse(receiptResponse.getId(), receiptDetailProductResponses,
                totalPrice, null, 0D);
    }

    private String calculateTotalMoney(List<ReceiptDetailProductResponse> receiptDetailProductResponses) {

        BigInteger totalPrice = BigInteger.ZERO;
        for (ReceiptDetailProductResponse receiptDetailProductResponse : receiptDetailProductResponses) {
            totalPrice = totalPrice.add(BigInteger.valueOf(
                    Long.parseLong(receiptDetailProductResponse.getPrice())
                            * receiptDetailProductResponse.getQuantity()));
        }
        return totalPrice.toString();
    }
}
