package shopping.exception.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ExceptionResponse {

    private final String message;

    @JsonCreator
    public ExceptionResponse(final String message) {
        this.message = message;
    }

    public static ExceptionResponse of(final Exception exception) {
        return new ExceptionResponse(exception.getMessage());
    }

    public ExceptionResponse(final Exception exception) {
        this(exception.getMessage());
    }

    public String getMessage() {
        return this.message;
    }
}
