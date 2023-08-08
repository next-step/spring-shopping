package shopping.exception;

public class CartItemNotFoundException extends ShoppingException {

    public CartItemNotFoundException(final long cartItemId) {
        super(message(cartItemId));
    }

    private static String message(final long cartItemId) {
        return String.format("%s - 요청 장바구니 id: %d", ErrorType.CART_ITEM_NO_EXIST.getMessage(), cartItemId);
    }
}
