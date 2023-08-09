package shopping.domain.cart;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CartItems {

    private final List<CartItem> items;

    public CartItems(final List<CartItem> items) {
        this.items = items;
    }

    public CartItem add(final CartItem item) {
        Optional<CartItem> itemOptional = findSameProduct(item);

        if (itemOptional.isPresent()) {
            CartItem oldItem = itemOptional.get();
            oldItem.increaseQuantity();
            return oldItem;
        }

        items.add(item);
        return item;
    }

    public Optional<CartItem> findSameProduct(final CartItem item) {
        return items.stream()
                .filter(item::checkSameProduct)
                .findAny();
    }

    public boolean contains(final CartItem cartItem) {
        return items.contains(cartItem);
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public long calculateTotalPrice() {
        return items.stream()
                .mapToLong(CartItem::calculateTotalPrice)
                .sum();
    }
}
