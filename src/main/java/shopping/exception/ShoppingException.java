package shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class ShoppingException extends RuntimeException {

    private final ExceptionType exceptionType;
    private final String causeValue;

    public ShoppingException(
        final ExceptionType exceptionType,
        final Object causeValue
    ) {
        this.exceptionType = exceptionType;
        this.causeValue = causeValue == null ? "" : causeValue.toString();
    }

    public ShoppingException(
        final ExceptionType exceptionType
    ) {
        this(exceptionType, "");
    }

    public HttpStatus getHttpStatus() {
        return exceptionType.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return exceptionType.getMessage();
    }

    public String getMessageWithCauseValue() {
        if (StringUtils.hasText(causeValue)) {
            return exceptionType.getMessage() + " cause value = " + causeValue;
        }

        return exceptionType.getMessage();
    }
}
