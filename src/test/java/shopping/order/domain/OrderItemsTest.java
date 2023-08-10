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
        orderItems.add(makeOrderItem("테스트 상품1", "1000.00", 3));
        orderItems.add(makeOrderItem("테스트 상품2", "2000.00", 2));
        orderItems.add(makeOrderItem("테스트 상품3", "500.00", 10));

        Money allOrderMoney = orderItems.getAllOrderMoney();

        Assertions.assertThat(allOrderMoney).isEqualTo(new Money("12000.00"));
    }

    private OrderItem makeOrderItem(String itemName, String amount, int quantity) {
        return new OrderItem(
            1L,
            itemName,
            new Money(amount),
            new Image("image"),
            new Quantity(quantity)
        );
    }
}
