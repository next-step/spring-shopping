package shopping.core.util;

public class ErrorTemplate {

    private String message;

    ErrorTemplate() {
    }

    public ErrorTemplate(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
