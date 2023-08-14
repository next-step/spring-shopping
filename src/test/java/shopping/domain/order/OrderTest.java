package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static shopping.fixture.MemberFixture.createMember;
import static shopping.fixture.ProductFixture.CHICKEN_PRICE;
import static shopping.fixture.ProductFixture.PIZZA_PRICE;
import static shopping.fixture.ProductFixture.createChicken;
import static shopping.fixture.ProductFixture.createPizza;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Quantity;
import shopping.domain.member.Member;

class OrderTest {

    @Test
    @DisplayName("Order의 총 금액을 계산한다.")
    void getOrderTotalPrice() {
        final int chickenQuantity = 11;
        final int pizzaQuantity = 10;
        final Member member = createMember(1L);
        final List<OrderItem> orderItems = List.of(
                new OrderItem(new CartItem(member, createChicken(), Quantity.from(chickenQuantity))),
                new OrderItem(new CartItem(member, createPizza(), Quantity.from(pizzaQuantity)))
        );

        Order order = new Order(member, orderItems);

        assertThat(order.getOrderTotalPrice()).isEqualTo(CHICKEN_PRICE * chickenQuantity + PIZZA_PRICE * pizzaQuantity);
    }
}
