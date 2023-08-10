package shopping.order.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.cart.domain.vo.Quantity;
import shopping.common.vo.Image;
import shopping.common.vo.Money;

class OrderItemTest {

    @Test
    @DisplayName("현재 주문 상품의 총 금액을 리턴한다.")
    void getTotalPrice() {
        OrderItem orderItem = new OrderItem(1L, "테스트 상품", new Money("1000.00"), new Image("image"), new Quantity(3));

        Money totalPrice = orderItem.getTotalPrice();

        Assertions.assertThat(totalPrice).isEqualTo(new Money("3000.00"));
    }
}
