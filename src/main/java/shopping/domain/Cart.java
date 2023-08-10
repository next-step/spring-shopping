package shopping.domain;

import java.util.List;
import shopping.exception.ArgumentValidateFailException;
import shopping.exception.EmptyCartException;

public class Cart {

    private final List<CartItem> cartItems;

    private final User user;

    public Cart(List<CartItem> cartItems, User user) {
        validateCartItems(cartItems);
        validateUser(user);
        this.cartItems = cartItems;
        this.user = user;
    }

    private static void validateCartItems(List<CartItem> cartItems) {
        if (cartItems == null) {
            throw new ArgumentValidateFailException("카트 아이템 리스트는 null일 수 없습니다.");
        }
        if (cartItems.isEmpty()) {
            throw new EmptyCartException();
        }
    }

    private static void validateUser(User user) {
        if (user == null) {
            throw new ArgumentValidateFailException("유저는 null일 수 없습니다.");
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public Order toOrderWithRatio(Ratio ratio) {
        Order order = new Order(user, getTotalPrice(), ratio);
        cartItems.forEach(cartItem -> order.addOrderItem(new OrderItem(order, cartItem)));
        return order;
    }

    private Price getTotalPrice() {
        return new Price(cartItems.stream()
                .mapToLong(CartItem::totalPrice)
                .sum());
    }
}
