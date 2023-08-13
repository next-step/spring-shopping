package shopping.dto.web.response;

public class ErrorResponse {

    private final String message;

    public ErrorResponse() {
        this("예상치 못한 에러가 발생하였습니다.");
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
