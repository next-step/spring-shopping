package shopping.exception.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ExceptionResponse {

    private final String message;

    @JsonCreator
    public ExceptionResponse(final Exception exception) {
        this.message = exception.getMessage();
    }

    public String getMessage() {
        return this.message;
    }
}
