package shopping.core.exception;

public class BadRequestException extends RuntimeException {

    private final String status;

    public BadRequestException(String message, String status) {
        super(message);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
