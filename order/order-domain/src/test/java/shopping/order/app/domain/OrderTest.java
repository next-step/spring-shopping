package shopping.order.app.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.order.app.exception.EmptyOrderException;

@DisplayName("Order 클래스")
class OrderTest {

    @Nested
    @DisplayName("new 생성자는")
    class new_constructor {

        @Test
        @DisplayName("userId와 구매할 Product들을 받아, 총 결제금액이 계산된 order를 생성한다.")
        void receive_cart_and_create_order_with_price_calculated() {
            // given
            long userId = 1L;
            Product product = DomainFixture.Product.defaultProduct();
            long defaultPrice = 1;
            int quantity = 1000;
            String expected = String.valueOf(defaultPrice * quantity);

            // when
            Order result = new Order(userId, Map.of(product, quantity));

            // then
            assertThat(result.getTotalPrice()).isEqualTo(expected);
        }

        @Test
        @DisplayName("구매할 product가 null 이라면, EmptyCartException을 던진다.")
        void throw_empty_cart_exception_when_input_null_cart() {
            // given
            long userId = 1L;

            // when
            Exception exception = catchException(() -> new Order(userId, null));

            // then
            assertThat(exception).isInstanceOf(EmptyOrderException.class);
        }

        @Test
        @DisplayName("cart에 어떠한 아이템도 없다면, EmptyCartException을 던진다.")
        void throw_empty_cart_exception_when_input_empty_cart() {
            // given
            long userId = 1L;

            // when
            Exception exception = catchException(() -> new Order(userId, Map.of()));

            // then
            assertThat(exception).isInstanceOf(EmptyOrderException.class);
        }

    }

    @Nested
    @DisplayName("purchase 메소드는")
    class purchase_method {

        private final Exchange defaultExchange = new Exchange(1D);

        @Test
        @DisplayName("구매에 성공하면, Receipt를 생성해서 반환한다.")
        void return_receipt_when_purchase_success() {
            // given
            long userId = 1L;
            Product product = DomainFixture.Product.defaultProduct();
            int productAmount = 1;
            BigDecimal exchangedPrice = BigDecimal.ONE;

            Order order = new Order(userId, Map.of(product, productAmount));

            Receipt expected = DomainFixture.Receipt.from(order, exchangedPrice, 1D, Map.of(product, productAmount));

            // when
            Receipt result = order.purchase(defaultExchange);

            // then
            assertReceipt(expected, result);
        }

        private void assertReceipt(Receipt expected, Receipt result) {
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(expected.getId()).isEqualTo(result.getId());
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

    }

}
