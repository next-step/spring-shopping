package shopping.domain;

import java.util.Objects;
import shopping.domain.entity.CartItemEntity;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

public final class CartItem {

    private final static int QUANTITY_ZERO = 0;
    private static final int ITEM_MIN_QUANTITY = 0;
    private static final int ITEM_MAX_QUANTITY = 1000;

    private final Long id;
    private final Long userId;
    private final Long productId;
    private final int quantity;

    private CartItem(final Long id, final Long userId, final Long productId, final int quantity) {
        validateQuantity(quantity);
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static CartItem from(final CartItemEntity cartItemEntity) {
        return new CartItem(
            cartItemEntity.getId(),
            cartItemEntity.getUser().getId(),
            cartItemEntity.getProduct().getId(),
            cartItemEntity.getQuantity());
    }

    public static CartItem of(final CartItemEntity cartItemEntity, final int quantity) {
        return new CartItem(
            cartItemEntity.getId(),
            cartItemEntity.getUser().getId(),
            cartItemEntity.getProduct().getId(),
            quantity);
    }

    private void validateQuantity(final int quantity) {
        if (isOutOfBound(quantity)) {
            throw new ShoppingException(ErrorCode.INVALID_CART_ITEM_QUANTITY);
        }
    }

    private boolean isOutOfBound(final int quantity) {
        return (quantity < ITEM_MIN_QUANTITY || ITEM_MAX_QUANTITY < quantity);
    }

    public void matchUser(final Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw new ShoppingException(ErrorCode.INVALID_CART_ITEM);
        }
    }

    public boolean isQuantityZero() {
        return (this.quantity == QUANTITY_ZERO);
    }

}
