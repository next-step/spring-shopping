package shopping.mart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.core.exception.BadRequestException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@DisplayName("Order 클래스")
class OrderTest {

    @Nested
    @DisplayName("생성자는")
    class constructor_method {

        @Test
        @DisplayName("Product 리스트를 받아 총 결제 금액을 계산한다.")
        void calculate_total_price_by_product_list() {
            // given
            Map<Product, Integer> products = Map.of(
                    new Product("소주", "images/soju.jpeg", "5000"), 1,
                    new Product("맥주", "images/beer.jpeg", "5500"), 1,
                    new Product("막걸리", "images/makgeolli.png", "6000"), 2
            );
            CurrencyRate currencyRate = new CurrencyRate(1000.0);

            String expectedTotalPrice = products.entrySet().stream()
                                                .map(item -> new BigInteger(item.getKey().getPrice()).multiply(new BigInteger(item.getValue().toString())))
                                                .reduce(BigInteger::add)
                                                .get()
                                                .toString();

            // when
            Order order = new Order(products, currencyRate.getValue());

            // then
            assertThat(order.getTotalPrice()).isEqualTo(expectedTotalPrice);
        }

        @Test
        @DisplayName("빈 Product 리스트를 받으면 BadRequestException을 던진다.")
        void throw_BadRequestException_when_product_list_is_null_or_empty() {
            // given
            Map<Product, Integer> products = Map.of();
            CurrencyRate currencyRate = new CurrencyRate(1000.0);

            // when
            Exception exception = catchException(() -> new Order(products, currencyRate.getValue()));

            // then
            assertThat(exception).isExactlyInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    @DisplayName("getUsd 메서드는")
    class getUsd_method {


        @Test
        @DisplayName("totalPrice와 currencyByUsd로 USD를 계산한다.")
        void calculate_usd_by_total_price_and_currencyByUsd() {
            // given
            Map<Product, Integer> products = Map.of(
                    new Product("소주", "images/soju.jpeg", "5000"), 1);
            CurrencyRate currencyRate = new CurrencyRate(1000.00);

            BigDecimal expectedUsd = new BigDecimal("5000")
                    .divide(BigDecimal.valueOf(1000.00), 2, RoundingMode.HALF_UP);

            // when
            Order order = new Order(products, currencyRate.getValue());

            // then
            assertThat(order.getUsd()).isEqualTo(expectedUsd.toString());
        }

        @Test
        @DisplayName("currencyByUsd가 null이면 null을 반환한다.")
        void return_null_when_currencyByUsd_is_null() {
            // given
            Map<Product, Integer> products = Map.of(
                    new Product("소주", "images/soju.jpeg", "5000"), 1);
            CurrencyRate currencyRate = new CurrencyRate(null);

            // when
            Order order = new Order(products, currencyRate.getValue());

            // then
            assertThat(order.getUsd()).isNull();
        }
    }
}
