package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.exception.ArgumentValidateFailException;
import shopping.exception.EmptyCartException;

@DisplayName("장바구니 객체 테스트")
public class CartTest {

    @Nested
    @DisplayName("장바구니 생성 검증")
    class WhenCreate {

        @DisplayName("장바구니 생성 성공")
        @Test
        void createSuccess() {
            // given
            User user = new User(1L, "user@email.com", "userpassword");
            Product product = new Product(1L, "product1", "productImage.jpg", 10_000L);
            List<CartItem> cartItems = List.of(
                    new CartItem(1L, user, product, 1)
            );

            // when
            Cart cart = new Cart(cartItems, user);

            // then
            assertThat(cart.getCartItems()).hasSize(1);
        }

        @DisplayName("장바구니 아이템 리스트가 null 이면 예외 발생")
        @Test
        void nullListThenThrow() {
            // given
            List<CartItem> cartItems = null;
            User user = new User(1L, "user@email.com", "userpassword");

            // when, then
            assertThatCode(() -> new Cart(cartItems, user))
                    .isInstanceOf(ArgumentValidateFailException.class)
                    .hasMessage("카트 아이템 리스트는 null일 수 없습니다.");

        }

        @DisplayName("장바구니 아이템 리스트가 비어있으면 예외 발생")
        @Test
        void emptyListThenThrow() {
            // given
            List<CartItem> cartItems = List.of();
            User user = new User(1L, "user@email.com", "userpassword");

            // when, then
            assertThatCode(() -> new Cart(cartItems, user))
                    .isInstanceOf(EmptyCartException.class);
        }

    }

    @Nested
    @DisplayName("장바구니에서 주문 생성")
    class WhenToOrder {

        @DisplayName("장바구니 주문 생성 성공")
        @Test
        void toOrderSuccess() {
            // given
            User user = new User(1L, "user@email.com", "userpassword");
            Product product1 = new Product(1L, "product1", "productImage.jpg", 10_000L);
            Product product2 = new Product(2L, "product1", "productImage.jpg", 10_011L);
            List<CartItem> cartItems = List.of(
                    new CartItem(1L, user, product1, 1),
                    new CartItem(2L, user, product2, 2)
            );
            Cart cart = new Cart(cartItems, user);

            // when
            Order order = cart.toOrder();

            // then
            assertThat(order.getTotalPrice().getPrice()).isEqualTo(30_022L);
            assertThat(order.getOrderItems()).hasSize(2);
        }

    }
}
