package shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class ShoppingException extends RuntimeException {

    private final ExceptionType cartExceptionType;
    private final String causeValue;

    public ShoppingException(
        final ExceptionType cartExceptionType,
        final Object causeValue
    ) {
        this.cartExceptionType = cartExceptionType;
        this.causeValue = causeValue == null ? "" : causeValue.toString();
    }

    public ShoppingException(
        final ExceptionType cartExceptionType
    ) {
        this(cartExceptionType, "");
    }

    public HttpStatus getHttpStatus() {
        return cartExceptionType.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return cartExceptionType.getMessage();
    }

    public String getMessageWithCauseValue() {
        if (StringUtils.hasText(causeValue)) {
            return cartExceptionType.getMessage() + " cause value = " + causeValue;
        }

        return cartExceptionType.getMessage();
    }
}
