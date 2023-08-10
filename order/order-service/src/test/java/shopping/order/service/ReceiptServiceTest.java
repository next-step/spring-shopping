package shopping.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.order.app.api.receipt.ReceiptUseCase;
import shopping.order.app.api.receipt.response.ReceiptDetailProductResponse;
import shopping.order.app.api.receipt.response.ReceiptDetailResponse;
import shopping.order.app.api.receipt.response.ReceiptProductResponse;
import shopping.order.app.api.receipt.response.ReceiptResponse;
import shopping.order.app.domain.Receipt;
import shopping.order.app.domain.ReceiptProduct;
import shopping.order.app.exception.DoesNotFindReceiptException;
import shopping.order.app.spi.ReceiptRepository;

@ExtendWith(SpringExtension.class)
@DisplayName("ReceiptService 클래스")
@ContextConfiguration(classes = ReceiptService.class)
class ReceiptServiceTest {

    @Autowired
    private ReceiptUseCase receiptUseCase;

    @MockBean
    private ReceiptRepository receiptRepository;

    @Nested
    @DisplayName("getByIdAndUserId 메소드는")
    class get_receipt_by_id_and_user_id_method {

        @Test
        @DisplayName("receiptId와 userId에 일치하는 Receipt를 찾을 수 없는경우, DoesNotFindReceiptException을 던진다.")
        void throw_does_not_find_receipt_exception_when_cannot_find_matched_receipt() {
            // given
            long id = 1L;
            long userId = 2L;

            when(receiptRepository.findByIdAndUserId(id, userId)).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> receiptUseCase.getByIdAndUserId(id, userId));

            // then
            assertThat(exception).isInstanceOf(DoesNotFindReceiptException.class);
        }

        @Test
        @DisplayName("receiptId와 userId를 받아, ReceiptDetailResponse를 반환한다.")
        void return_receipt_detail_response_when_input_receipt_id_and_user_id() {
            // given
            long id = 1L;
            long userId = 2L;
            long productId = 3L;

            List<ReceiptProduct> receiptProducts = List.of(
                    new ReceiptProduct(productId, "receipt", BigInteger.ONE, "default-image", 10)
            );
            Receipt receipt = new Receipt(id, userId, receiptProducts, BigInteger.ONE, BigDecimal.ONE, 1.0D);

            when(receiptRepository.findByIdAndUserId(id, userId)).thenReturn(Optional.of(receipt));

            ReceiptDetailResponse expected = getReceiptDetailResponse(receipt);

            // when
            ReceiptDetailResponse result = receiptUseCase.getByIdAndUserId(id, userId);

            // then
            assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        }

        private ReceiptDetailResponse getReceiptDetailResponse(Receipt receipt) {
            ReceiptProduct receiptProduct = receipt.getReceiptProducts().get(0);
            List<ReceiptDetailProductResponse> receiptDetailProductResponses = List.of(
                    new ReceiptDetailProductResponse(receiptProduct.getProductId(), receiptProduct.getName(),
                            receiptProduct.getPrice().toString(), receiptProduct.getImageUrl(),
                            receiptProduct.getQuantity())
            );
            return new ReceiptDetailResponse(receipt.getId(), receiptDetailProductResponses,
                    receipt.getTotalPrice().toString(),
                    receipt.getExchangedPrice().toString(), receipt.getExchangeRate());
        }

    }

    @Nested
    @DisplayName("findAllByUserId 메소드는")
    class find_all_by_user_id_method {

        @Test
        @DisplayName("userId에 해당하는 구매내역을 찾을 수 없으면, 빈 리스트를 반환한다.")
        void return_empty_list_when_cannot_find_purchase_history() {
            // given
            long userId = 1L;

            when(receiptRepository.findAllByUserId(userId)).thenReturn(List.of());

            // when
            List<ReceiptResponse> result = receiptUseCase.findAllByUserId(userId);

            // then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("userId를 받아, ReceiptResponse 를 반환한다.")
        void return_receipt_response_when_input_user_id() {
            // given
            long id = 1L;
            long userId = 2L;
            long productId = 3L;

            List<ReceiptProduct> receiptProducts = List.of(
                    new ReceiptProduct(productId, "receipt", BigInteger.ONE, "default-image", 10)
            );

            Receipt receipt = new Receipt(id, userId, receiptProducts, BigInteger.ONE, BigDecimal.ONE, 1.0D);

            when(receiptRepository.findAllByUserId(userId)).thenReturn(List.of(receipt));

            List<ReceiptResponse> expected = getReceiptResponse(receipt);

            // when
            List<ReceiptResponse> result = receiptUseCase.findAllByUserId(userId);

            // then
            assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        }

        private List<ReceiptResponse> getReceiptResponse(Receipt receipt) {
            ReceiptProduct receiptProduct = receipt.getReceiptProducts().get(0);
            List<ReceiptProductResponse> receiptProductResponses = List.of(
                    new ReceiptProductResponse(receiptProduct.getProductId(), receiptProduct.getName(),
                            receiptProduct.getPrice().toString(), receiptProduct.getImageUrl(),
                            receiptProduct.getQuantity())
            );
            return List.of(new ReceiptResponse(receipt.getId(), receiptProductResponses));
        }

    }

}
