package shopping.common.exception;

public class ErrorResponse {

    private ErrorCode errorCode;
    private String message;

    private ErrorResponse() {
    }

    private ErrorResponse(final ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public static ErrorResponse from(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
