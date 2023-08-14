package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static shopping.fixture.MemberFixture.createMember;
import static shopping.fixture.ProductFixture.CHICKEN_PRICE;
import static shopping.fixture.ProductFixture.createChicken;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Quantity;
import shopping.domain.member.Member;
import shopping.domain.product.Product;

class OrderItemTest {

    @Test
    @DisplayName("OrderItem의 총 금액을 계산한다.")
    void getOrderItemTotalPrice() {
        final int quantity = 10;
        final Member member = createMember(1L);
        final Product product = createChicken();
        final CartItem cartItem = new CartItem(member, product, Quantity.from(quantity));

        final OrderItem orderItem = new OrderItem(cartItem);

        assertThat(orderItem.getOrderItemTotalPrice()).isEqualTo(CHICKEN_PRICE * quantity);
    }
}
