package shopping.core.advice;

public class ErrorTemplate {

    private String statusCode;
    private String message;

    ErrorTemplate() {
    }

    ErrorTemplate(final String statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
