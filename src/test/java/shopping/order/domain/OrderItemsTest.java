package shopping.order.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.cart.domain.vo.Quantity;
import shopping.common.vo.Image;
import shopping.common.vo.Money;

class OrderItemsTest {

    @Test
    @DisplayName("주문 상품들의 전체 price를 반환한다.")
    void getAllPrice() {
        OrderItems orderItems = new OrderItems();
        orderItems.add(new OrderItem(1L, "테스트 상품1", new Money("1000.00"), new Image("image") , new Quantity(3), null));
        orderItems.add(new OrderItem(1L, "테스트 상품2", new Money("2000.00"), new Image("image"), new Quantity(2), null));
        orderItems.add(new OrderItem(1L, "테스트 상품3", new Money("500.00"), new Image("image"), new Quantity(10), null));

        Money allOrderMoney = orderItems.getAllOrderMoney();

        Assertions.assertThat(allOrderMoney).isEqualTo(new Money("12000.00"));
    }
}
