package shopping.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.cart.domain.Cart;
import shopping.cart.domain.CartItem;
import shopping.member.domain.Member;
import shopping.order.domain.Order;
import shopping.product.domain.Product;
import shopping.product.domain.vo.Money;

@DisplayName("OrderMapper 단위 테스트")
class OrderMapperTest {

    @Test
    @DisplayName("Cart를 Order로 변환한다.")
    void mapToOrder() {
        // given
        OrderMapper orderMapper = new OrderMapper();
        Product product1 = new Product(1L, "피자", "imageUrl", "10000");
        Product product2 = new Product(2L, "치킨", "imageUrl", "20000");
        Member member = new Member(1L, "email", "password");
        Cart cart = new Cart(1L, List.of(new CartItem(product1, member), new CartItem(product2, member)));

        // when
        Order order = orderMapper.mapToOrder(member.getId(), cart);

        // then
        assertThat(order.getOrderItems()).hasSize(2);
        assertThat(order.getTotalPrice()).isEqualTo(new Money("30000"));
    }
}
