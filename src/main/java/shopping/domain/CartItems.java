package shopping.domain;

import shopping.domain.entity.CartItem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CartItems {

    private final List<CartItem> items;

    public CartItems(final List<CartItem> items) {
        this.items = items;
    }

    public void add(final CartItem item) {
        findSameProduct(item).ifPresentOrElse(CartItem::increaseQuantity, () -> items.add(item));
    }

    public boolean contains(final CartItem cartItem) {
        return items.contains(cartItem);
    }

    public Optional<CartItem> find(final Long productId) {
        return items.stream()
                .filter(cartItem -> cartItem.equalsByProductId(productId))
                .findAny();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private Optional<CartItem> findSameProduct(final CartItem item) {
        return items.stream()
                .filter(item::checkSameProduct)
                .findAny();
    }
}
