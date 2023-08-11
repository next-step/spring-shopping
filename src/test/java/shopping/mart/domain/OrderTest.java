package shopping.mart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
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
            List<Product> products = List.of(
                    new Product("소주", "images/soju.jpeg", "5000"),
                    new Product("맥주", "images/beer.jpeg", "5500"),
                    new Product("막걸리", "images/makgeolli.png", "6000")
            );
            String expectedTotalPrice = products.stream()
                    .map(product -> new BigInteger(product.getPrice()))
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
            List<Product> products = Collections.emptyList();

            // when
            Exception exception = catchException(() -> new Order(products));

            // then
            assertThat(exception).isExactlyInstanceOf(BadRequestException.class);
        }
    }
}
