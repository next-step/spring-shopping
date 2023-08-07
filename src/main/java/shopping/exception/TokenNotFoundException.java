package shopping.exception;

public class TokenNotFoundException extends ShoppingException {

    public TokenNotFoundException() {
        super(ErrorType.NO_TOKEN);
    }
}
