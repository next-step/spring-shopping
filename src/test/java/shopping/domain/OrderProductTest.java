package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.exception.OrderProductException;

@DisplayName("OrderProduct 클래스")
public class OrderProductTest {

    @Nested
    @DisplayName("new 생성자는")
    class OrderProduct_Constructor {

        @Test
        @DisplayName("OrderProduct 를 생성한다.")
        void createOrderProduct() {
            // given
            Member member = new Member("home@naver.com", "1234");
            Order order = new Order(member);
            Product product = new Product("치킨", "image", 23000L);
            int quantity = 10;

            // when
            Exception exception = catchException(() -> new OrderProduct(order, product, quantity));

            // then
            assertThat(exception).isNull();
        }

        @Test
        @DisplayName("Order 가 null 이면 OrderProductException 을 던진다.")
        void throwOrderProductException_whenOrderIsNull() {
            // given
            Order order = null;
            Product product = new Product("치킨", "image", 23000L);
            int quantity = 10;

            // when
            Exception exception = catchException(() -> new OrderProduct(order, product, quantity));

            // then
            assertThat(exception).isInstanceOf(OrderProductException.class);
            assertThat(exception.getMessage()).contains("order 가 존재하지 않습니다");
        }

        @Test
        @DisplayName("Product 가 null 이면 OrderProductException 을 던진다.")
        void throwOrderProductException_whenProductIsNull() {
            // given
            Member member = new Member("home@naver.com", "1234");
            Order order = new Order(member);
            Product product = null;
            int quantity = 10;

            // when
            Exception exception = catchException(() -> new OrderProduct(order, product, quantity));

            // then
            assertThat(exception).isInstanceOf(OrderProductException.class);
            assertThat(exception.getMessage()).contains("product 가 존재하지 않습니다");
        }

        @Test
        @DisplayName("수량이 0 이하이면 OrderProductException 을 던진다.")
        void throwOrderException_whenQuantityIsNotPositive() {
            // given
            Member member = new Member("home@naver.com", "1234");
            Order order = new Order(member);
            Product product = new Product("치킨", "image", 23000L);
            int quantity = 0;

            // when
            Exception exception = catchException(() -> new OrderProduct(order, product, quantity));

            // then
            assertThat(exception).isInstanceOf(OrderProductException.class);
            assertThat(exception.getMessage()).contains("상품의 개수는 최소 1개여야합니다");
        }
    }

}
