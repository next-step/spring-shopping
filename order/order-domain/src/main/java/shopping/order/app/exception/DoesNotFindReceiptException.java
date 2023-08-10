package shopping.order.app.exception;

import java.text.MessageFormat;

public final class DoesNotFindReceiptException extends RuntimeException {

    public DoesNotFindReceiptException(long receiptId) {
        super(MessageFormat.format("receiptId \"{0}\" 에 해당하는 Receipt를 찾을 수 없습니다.", receiptId));
    }

}
