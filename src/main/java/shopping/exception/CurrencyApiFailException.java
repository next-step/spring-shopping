package shopping.exception;

public class CurrencyApiFailException extends ShoppingBaseException {

    private static final int STATUS_CODE = 500;
    private static final String MESSAGE = "환율 API 호출에 실패하였습니다.";

    public CurrencyApiFailException() {
        super(MESSAGE, STATUS_CODE);
    }
}
