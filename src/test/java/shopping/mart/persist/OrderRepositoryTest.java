package shopping.mart.persist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import shopping.core.exception.BadRequestException;
import shopping.mart.domain.CurrencyRate;
import shopping.mart.domain.Order;
import shopping.mart.domain.Product;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("OrderRepository 클래스")
@ContextConfiguration(classes = {OrderRepository.class})
class OrderRepositoryTest extends JpaTest {

    @Autowired
    private OrderRepository orderRepository;

    @Nested
    @DisplayName("findOrderById 메서드는")
    class findOrderById_method {

        @Test
        @DisplayName("orderId에 해당하는 OrderEntity, OrderProductEntity를 조회하고 Order로 취합해 반환한다.")
        void find_and_return_Order_by_OrderEntity_and_OrderProductEntity() {
            // given
            Map<Product, Integer> products = Map.of(
                    new Product(1L, "소주", "images/soju.jpeg", "5000"), 1,
                    new Product(2L, "맥주", "images/beer.jpeg", "5500"), 1,
                    new Product(3L, "막걸리", "images/makgeolli.png", "6000"), 2
            );
            CurrencyRate currencyRate = new CurrencyRate(1000.0);
            Order order = new Order(products, currencyRate.getValue());
            Long orderId = orderRepository.order(1L, order);

            // when
            Order result = orderRepository.findOrderById(1L, orderId);

            // then
            assertEquals(order, result);
        }

        @Test
        @DisplayName("orderId에 해당하는 데이터가 없다면 BadRequestException을 던진다.")
        void throw_BadRequestException_when_not_exists_order() {
            // given
            Long notExistsId = 9999L;

            // when
            Exception exception = catchException(() -> orderRepository.findOrderById(1L, notExistsId));

            // then
            assertThat(exception).isExactlyInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    @DisplayName("order 메서드는")
    class order_method {

        @Test
        @DisplayName("Order를 받아 OrderEntity, OrderProductEntity를 저장하고 orderId를 반환한다.")
        void save_OrderEntity_and_OrderProductEntity_from_Order() {
            // given
            Map<Product, Integer> products = Map.of(
                    new Product(1L, "소주", "images/soju.jpeg", "5000"), 1,
                    new Product(2L, "맥주", "images/beer.jpeg", "5500"), 1,
                    new Product(3L, "막걸리", "images/makgeolli.png", "6000"), 2
            );
            CurrencyRate currencyRate = new CurrencyRate(1000.0);
            Order order = new Order(products, currencyRate.getValue());

            // when
            Long orderId = orderRepository.order(1L, order);

            // then
            Order result = orderRepository.findOrderById(1L, orderId);
            assertEquals(order, result);
        }
    }
}
