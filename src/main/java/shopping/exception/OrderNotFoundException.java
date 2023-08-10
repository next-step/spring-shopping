package shopping.exception;

public class OrderNotFoundException extends ShoppingException {

    public OrderNotFoundException() {
        super(ErrorType.ORDER_NO_EXIST);
    }
}
