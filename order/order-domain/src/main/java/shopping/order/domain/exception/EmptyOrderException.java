package shopping.order.domain.exception;

public final class EmptyOrderException extends RuntimeException {

    public EmptyOrderException() {
        super("Cart에 아이템이 없습니다.");
    }
}
