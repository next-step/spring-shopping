package shopping.exception;

public class ApiExceptionResponse {
    private String message;

    private ApiExceptionResponse() {
    }

    private ApiExceptionResponse(final String message) {
        this.message = message;
    }

    public static ApiExceptionResponse from(final String message) {
        return new ApiExceptionResponse(message);
    }

    public String getMessage() {
        return this.message;
    }
}
