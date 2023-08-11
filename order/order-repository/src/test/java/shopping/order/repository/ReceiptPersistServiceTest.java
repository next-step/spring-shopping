package shopping.order.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import shopping.mart.domain.usecase.product.ProductUseCase;
import shopping.mart.domain.usecase.product.response.ProductResponse;
import shopping.order.domain.Exchange;
import shopping.order.domain.Order;
import shopping.order.domain.Product;
import shopping.order.domain.Receipt;
import shopping.order.domain.ReceiptProduct;
import shopping.order.repository.entity.ReceiptEntity;
import shopping.order.repository.entity.ReceiptProductEntity;

@DisplayName("ReceiptPersistService 클래스")
@ContextConfiguration(classes = ReceiptPersistService.class)
public class ReceiptPersistServiceTest extends JpaTest {

    @Autowired
    private ReceiptPersistService receiptPersistService;

    @Autowired
    private ReceiptJpaRepository receiptJpaRepository;

    @MockBean
    private ProductUseCase productUseCase;

    private static final Exchange defaultExchange = new Exchange(1D);

    @Nested
    @DisplayName("persist 메소드는")
    class persist_method {

        @Test
        @DisplayName("Receipt 도메인을 받아 영속화 한다.")
        void persist_receipt_when_receive_receipt() {
            // given
            long userId = 1L;
            int quantity = 1;
            Product product = DomainFixture.Product.defaultProduct();
            Order order = new Order(userId, Map.of(product, quantity));
            Receipt receipt = order.purchase(defaultExchange);
            ReceiptEntity expected = new ReceiptEntity(receipt);

            // when
            receiptPersistService.persist(receipt);
            List<ReceiptEntity> result = receiptJpaRepository.findAll();

            // then
            assertReceiptEntityExceptId(result.get(0), expected);
        }

        private void assertReceiptEntityExceptId(ReceiptEntity result, ReceiptEntity expected) {
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getTotalPrice()).isEqualTo(expected.getTotalPrice());
                assertExactlyReceiptProductEntity(result.getReceiptProductEntities(),
                        expected.getReceiptProductEntities());
            });
        }

        private void assertExactlyReceiptProductEntity(List<ReceiptProductEntity> result,
                List<ReceiptProductEntity> expected) {

            assertThat(result).hasSize(expected.size());
            SoftAssertions.assertSoftly(softAssertions -> {
                for (int i = 0; i < result.size(); i++) {
                    ReceiptProductEntity resultElement = result.get(i);
                    ReceiptProductEntity expectedElement = expected.get(i);
                    softAssertions.assertThat(resultElement.getQuantity()).isEqualTo(expectedElement.getQuantity());
                }
            });
        }

    }

    @Nested
    @DisplayName("findAllByUserId 메소드는")
    class find_all_by_user_id_method {

        @Test
        @DisplayName("userId에 해당하는 user가 구매한 모든 receipt를 반환한다.")
        void return_all_receipt_bought_by_user() {
            // given
            long userId = 1L;
            int quantity = 1;
            Product product = DomainFixture.Product.defaultProduct();
            Order order = new Order(userId, Map.of(product, quantity));
            Receipt receipt = order.purchase(defaultExchange);

            receiptPersistService.persist(receipt);
            receiptPersistService.persist(receipt);
            receiptPersistService.persist(receipt);

            when(productUseCase.findAllProducts()).thenReturn(defaultProductResponse(List.of(product)));

            // when
            List<Receipt> result = receiptPersistService.findAllByUserId(userId);

            // then
            assertReceipts(result, List.of(receipt, receipt, receipt));
        }

    }

    @Nested
    @DisplayName("findByIdAndUserId 메소드는")
    class find_by_id_and_user_id_method {

        @Test
        @DisplayName("userId와 receiptId 모두 일치하는 Receipt를 반환한다.")
        void return_receipt_matched_user_id_and_receipt_id() {
            // given
            long userId = 1L;
            int quantity = 1;
            Product product = DomainFixture.Product.defaultProduct();
            Order order = new Order(userId, Map.of(product, quantity));
            Receipt receipt = order.purchase(defaultExchange);

            receiptPersistService.persist(receipt);

            long receiptId = receiptJpaRepository.findAll().get(0).getId();
            userId = receipt.getUserId();

            when(productUseCase.findAllProducts()).thenReturn(defaultProductResponse(List.of(product)));

            // when
            Optional<Receipt> result = receiptPersistService.findByIdAndUserId(receiptId, userId);

            // then
            assertThat(result).isNotEmpty();
            assertReceiptWithOutId(result.get(), receipt);
        }

        @Test
        @DisplayName("userId와 receiptId 모두 일치하는 Receipt를 찾을 수 없으면, Optional.empty를 반환한다.")
        void return_empty_optional_if_cannot_find_matched_receipt() {
            // given
            long receiptId = 1L;
            long userId = 2L;

            // when
            Optional<Receipt> result = receiptPersistService.findByIdAndUserId(receiptId, userId);

            // then
            assertThat(result).isEmpty();
        }

    }

    private void assertReceipts(List<Receipt> result, List<Receipt> expected) {
        assertThat(result).hasSize(expected.size());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (int i = 0; i < result.size(); i++) {
                Receipt resultElement = result.get(i);
                Receipt expectedElement = expected.get(i);
                assertReceiptWithOutId(resultElement, expectedElement);
            }
        });
    }

    private void assertReceiptWithOutId(Receipt expected, Receipt result) {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(expected.getTotalPrice()).isEqualTo(result.getTotalPrice());
            assertExactlyReceiptProducts(expected.getReceiptProducts(), result.getReceiptProducts());
        });
    }

    private void assertExactlyReceiptProducts(List<ReceiptProduct> expected, List<ReceiptProduct> result) {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(expected).hasSize(result.size());
            for (int i = 0; i < expected.size(); i++) {
                ReceiptProduct expectedElement = expected.get(0);
                ReceiptProduct resultElement = result.get(0);
                softAssertions.assertThat(expectedElement.getName()).isEqualTo(resultElement.getName());
                softAssertions.assertThat(expectedElement.getPrice()).isEqualTo(resultElement.getPrice());
                softAssertions.assertThat(expectedElement.getImageUrl()).isEqualTo(resultElement.getImageUrl());
                softAssertions.assertThat(expectedElement.getQuantity()).isEqualTo(resultElement.getQuantity());
            }
        });
    }

    private List<ProductResponse> defaultProductResponse(List<Product> products) {
        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getImageUrl(),
                        product.getPrice().toString()))
                .collect(Collectors.toList());
    }

}
