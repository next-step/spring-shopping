package shopping.dto.response;

public class ErrorResponse {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    private ErrorResponse() {
    }

    public String getMessage() {
        return message;
    }
}
