package shopping.domain;

import java.util.List;
import shopping.exception.ArgumentValidateFailException;
import shopping.exception.EmptyCartException;

public class Cart {

    private final List<CartItem> cartItems;

    public Cart(List<CartItem> cartItems) {
        validate(cartItems);
        this.cartItems = cartItems;
    }

    private static void validate(List<CartItem> cartItems) {
        if (cartItems == null) {
            throw new ArgumentValidateFailException("카트 아이템 리스트는 null일 수 없습니다.");
        }
        if (cartItems.isEmpty()) {
            throw new EmptyCartException();
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public Price getTotalPrice() {
        return new Price(cartItems.stream()
                .mapToLong(CartItem::totalPrice)
                .sum());
    }


}
