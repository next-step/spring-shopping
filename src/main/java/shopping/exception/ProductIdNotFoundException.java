package shopping.exception;

public class ProductIdNotFoundException extends ShoppingException {

    public ProductIdNotFoundException() {
        super(ErrorType.PRODUCT_NULL);
    }
}
