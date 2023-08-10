package shopping.domain.entity;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.ExchangeRate;
import shopping.domain.entity.fixture.EntityFixture;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class OrderTest {

    @Test
    @DisplayName("주문을 생성한다")
    void createOrder() {
        // given
        final long userId = 1;
        final List<OrderItem> orderItems = List.of();
        final ExchangeRate exchangeRate = new ExchangeRate(1.0);

        // when & then
        assertThatNoException().isThrownBy(() -> Order.of(userId, orderItems, exchangeRate));
    }

    @Test
    @DisplayName("주문을 생성할 때 총 주문 금액 계산에 성공한다")
    void calculateTotalPrice() {
        // given
        final List<Integer> prices = List.of(2000, 3000);
        final List<Integer> quantities = List.of(2, 3);

        final List<OrderItem> orderItems = List.of(
                EntityFixture.createOrderItem(1, prices.get(0), quantities.get(0)),
                EntityFixture.createOrderItem(2, prices.get(1), quantities.get(1))
        );

        final ExchangeRate exchangeRate = new ExchangeRate(1.0);

        // when
        final Order order = Order.of(1L, orderItems, exchangeRate);

        // then
        assertThat(order.getTotalPrice().getPrice())
                .isEqualTo(prices.get(0) * quantities.get(0) + prices.get(1) * quantities.get(1));
    }

    @Test
    @DisplayName("환율이 적용 된 금액을 계산한다")
    void calculateTotalPriceWithExchangeRate() {
        // given
        final double exchangeRate = 1300.0;
        final int originPrice = 1000;

        final List<OrderItem> orderItems = List.of(
                EntityFixture.createOrderItem(1, originPrice, 1)
        );

        // when
        final Order order = Order.of(1L, orderItems, new ExchangeRate(exchangeRate));

        // then
        assertThat(order.applyExchangeRate()).isCloseTo(originPrice / exchangeRate, Percentage.withPercentage(99));
    }
}
