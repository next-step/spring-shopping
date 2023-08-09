package shopping.exception.infrastructure;

public class NoConnectionException extends Exception {

    private static final String MESSAGE = "외부 API와 연결이 불가능 합니다.";

    public NoConnectionException() {
        super(MESSAGE);
    }
}
