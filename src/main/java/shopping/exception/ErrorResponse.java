package shopping.exception;

public class ErrorResponse {

    private final String message;

    private ErrorResponse(final String message) {
        this.message = message;
    }

    public static ErrorResponse of(final String message) {
        return new ErrorResponse(message);
    }

    public String getMessage() {
        return message;
    }
}
