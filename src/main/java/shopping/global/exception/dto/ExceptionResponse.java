package shopping.global.exception.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ExceptionResponse {

    private final String message;

    @JsonCreator
    public ExceptionResponse(final String message) {
        this.message = message;
    }

    public ExceptionResponse(final Exception exception) {
        this(exception.getMessage());
    }

    public String getMessage() {
        return this.message;
    }
}
