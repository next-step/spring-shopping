package shopping.order.app.domain.exception;

public final class EmptyCartException extends RuntimeException {

    public EmptyCartException() {
        super("Cart에 아이템이 없습니다.");
    }
}
