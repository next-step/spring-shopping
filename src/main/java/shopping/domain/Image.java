package shopping.domain;

import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;

@Embeddable
public class Image {

    private static final int MAX_LENGTH = 255;

    private final String image;

    protected Image() {
        this.image = null;
    }

    public Image(String value) {
        validateLength(value);

        this.image = value;
    }

    private void validateLength(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new ShoppingException(ErrorType.IMAGE_TOO_LONG);
        }
    }

    public String getImage() {
        return image;
    }
}
