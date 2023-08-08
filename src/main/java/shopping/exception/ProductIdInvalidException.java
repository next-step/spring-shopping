package shopping.exception;

public class ProductIdInvalidException extends ShoppingException {

    public ProductIdInvalidException() {
        super(ErrorType.PRODUCT_INVALID);
    }
}
