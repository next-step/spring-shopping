package shopping.mart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import java.math.BigInteger;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.core.exception.BadRequestException;

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

            String expectedTotalPrice = products.entrySet().stream()
                    .map(item -> new BigInteger(item.getKey().getPrice()).multiply(new BigInteger(item.getValue().toString())))
                    .reduce(BigInteger::add)
                    .get()
                    .toString();

            // when
            Order order = new Order(products);

            // then
            assertThat(order.getTotalPrice()).isEqualTo(expectedTotalPrice);
        }

        @Test
        @DisplayName("빈 Product 리스트를 받으면 BadRequestException을 던진다.")
        void throw_BadRequestException_when_product_list_is_null_or_empty() {
            // given
            Map<Product, Integer> products = Map.of();

            // when
            Exception exception = catchException(() -> new Order(products));

            // then
            assertThat(exception).isExactlyInstanceOf(BadRequestException.class);
        }
    }
}
