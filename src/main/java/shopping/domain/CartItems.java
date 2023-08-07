package shopping.domain;

import shopping.domain.entity.CartItem;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

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

    public Optional<CartItem> findSameProduct(final CartItem item) {
        return items.stream()
                .filter(item::checkSameProduct)
                .findAny();
    }

    public void validateContains(final CartItem item) {
        if (!items.contains(item)) {
            throw new ShoppingException(ErrorType.USER_NOT_CONTAINS_ITEM, item.getId());
        }
    }

    public boolean contains(final CartItem cartItem) {
        return items.contains(cartItem);
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
