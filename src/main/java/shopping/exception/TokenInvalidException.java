package shopping.exception;

public class TokenInvalidException extends ShoppingException {

    public TokenInvalidException() {
        super(ErrorType.TOKEN_INVALID);
    }
}
