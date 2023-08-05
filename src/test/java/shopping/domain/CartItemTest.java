package shopping.domain;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("장바구니 물건 도메인 테스트")
class CartItemTest {

    @DisplayName("수량 변경 정상 작동 테스트")
    @Test
    void updateQuantityThenReturn() {
        // given
        User user = new User(1L, "hello@hello.hel", "123456789");
        Product product = new Product(1L, "name", "/image.jpg", 1000L);

        CartItem cartItem = new CartItem(1L, user, product, 1);

        // when
        CartItem quantityChanged = cartItem.updateQuantity(5);

        // then
        assertThat(quantityChanged.getQuantity()).isEqualTo(5);
    }

    @DisplayName("isDifferentUser 메소드 유저가 다를 시 참 리턴")
    @Test
    void isDifferentUserThenTrue() {
        // given
        User user1 = new User(1L, "hello@hello.hel", "123456789");
        User user2 = new User(2L, "hello2@hello.hel", "123456789");
        Product product = new Product(1L, "name", "/image.jpg", 1000L);

        CartItem cartItem = new CartItem(1L, user1, product, 1);

        // when
        boolean isDifferentUser = cartItem.isDifferentUser(user2);

        // then
        assertThat(isDifferentUser).isTrue();
    }

    @DisplayName("isDifferentUser 메소드 유저가 같을 시 참 리턴")
    @Test
    void isDifferentUserThenFalse() {
        // given
        User user1 = new User(1L, "hello@hello.hel", "123456789");
        Product product = new Product(1L, "name", "/image.jpg", 1000L);

        CartItem cartItem = new CartItem(1L, user1, product, 1);

        // when
        boolean isDifferentUser = cartItem.isDifferentUser(user1);

        // then
        assertThat(isDifferentUser).isFalse();
    }

    @DisplayName("장바구니 아이템 총 가격 계산 성공")
    @Test
    void whenTotalPriceThenReturnSuccess() {
        // given
        Long price = 1000L;
        Integer quantity = 3;

        User user = new User(1L, "hello@hello.hel", "123456789");
        Product product = new Product(1L, "name", "/image.jpg", price);
        CartItem cartItem = new CartItem(1L, user, product, quantity);

        // when
        Long totalPrice = cartItem.totalPrice();

        // then
        assertThat(totalPrice).isEqualTo(quantity * price);
    }
}
