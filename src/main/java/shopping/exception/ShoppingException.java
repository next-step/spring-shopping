package shopping.exception;

import org.springframework.http.HttpStatus;

public class ShoppingException extends RuntimeException {

    private final ShoppingErrorType shoppingErrorType;

    public ShoppingException(final ShoppingErrorType shoppingErrorType) {
        this.shoppingErrorType = shoppingErrorType;
    }

    public HttpStatus getHttpStatus() {
        return this.shoppingErrorType.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return shoppingErrorType.getMessage();
    }
}

