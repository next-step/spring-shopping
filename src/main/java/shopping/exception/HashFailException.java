package shopping.exception;

public class HashFailException extends ShoppingBaseException {

    private static final int STATUS_CODE = 500;
    private static final String MESSAGE = "패스워드 해시 과정에서 에러가 발생하였습니다.";

    public HashFailException() {
        super(MESSAGE, STATUS_CODE);
    }
}
