package shopping.exception;

public class ProductNotFoundException extends ShoppingException {

    public ProductNotFoundException(final long productId) {
        super(message(productId));
    }

    private static String message(final long productId) {
        return String.format("%s - 요청 상품 id: %d", ErrorType.PRODUCT_NO_EXIST, productId);
    }
}
