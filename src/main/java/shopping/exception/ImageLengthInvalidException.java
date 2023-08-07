package shopping.exception;

public class ImageLengthInvalidException extends ShoppingException {

    public ImageLengthInvalidException() {
        super(ErrorType.IMAGE_TOO_LONG);
    }
}
