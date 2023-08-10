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
    @DisplayName("of 메서드는")
    class Of_Method {

        @Test
        @DisplayName("OrderProduct 를 생성한다.")
        void createOrderProduct() {
            // given
            Member member = new Member("home@naver.com", "1234");
            Order order = new Order(member, 1300.0);
            Product product = new Product("치킨", "image", 23000L);
            int quantity = 10;
            CartProduct cartProduct = new CartProduct(member, product, quantity);

            // when
            Exception exception = catchException(() -> OrderProduct.of(order, cartProduct));

            // then
            assertThat(exception).isNull();
        }

        @Test
        @DisplayName("Order 가 null 이면 OrderProductException 을 던진다.")
        void throwOrderProductException_whenOrderIsNull() {
            // given
            Member member = new Member("home@naver.com", "1234");
            Order order = null;
            Product product = new Product("치킨", "image", 23000L);
            int quantity = 10;
            CartProduct cartProduct = new CartProduct(member, product, quantity);

            // when
            Exception exception = catchException(() -> OrderProduct.of(order, cartProduct));

            // then
            assertThat(exception).isInstanceOf(OrderProductException.class);
            assertThat(exception.getMessage()).contains("order 가 존재하지 않습니다");
        }

    }

}
