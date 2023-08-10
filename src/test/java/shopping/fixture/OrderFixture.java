package shopping.fixture;

import java.util.ArrayList;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;

public class OrderFixture {

    public static OrderItem createOrderItem(final CartItem cartItem, final Long id) {
        final OrderItem orderItem = new OrderItem(cartItem);
        ReflectionTestUtils.setField(orderItem, "id", id);
        return orderItem;
    }

    public static List<OrderItem> createOrderItems(final List<CartItem> cartItems, final Long startId) {
        final List<OrderItem> orderItems = new ArrayList<>();
        final int end = cartItems.size();
        for (int i = 0; i < end; i++) {
            orderItems.add(createOrderItem(cartItems.get(i), startId + i));
        }
        return orderItems;
    }

    public static Order createOrder(final Member member, final List<OrderItem> orderItems, final Long id) {
        final Order order = new Order(member, orderItems);
        ReflectionTestUtils.setField(order, "id", id);
        return order;
    }
}
