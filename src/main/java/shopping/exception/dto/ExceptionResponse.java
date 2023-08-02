package shopping.exception.dto;

public class ExceptionResponse {

    private final String message;

    public ExceptionResponse(final Exception exception) {
        this.message = exception.getMessage();
    }

    public String getMessage() {
        return this.message;
    }
}
