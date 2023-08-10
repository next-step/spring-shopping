package shopping.core.advice;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorTemplate)) {
            return false;
        }
        ErrorTemplate that = (ErrorTemplate) o;
        return Objects.equals(statusCode, that.statusCode) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message);
    }

    @Override
    public String toString() {
        return "ErrorTemplate{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
