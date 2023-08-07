package shopping.core.util;

public class ErrorTemplate {

    private final String message;

    public ErrorTemplate(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
