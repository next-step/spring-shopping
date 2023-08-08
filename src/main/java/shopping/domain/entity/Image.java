package shopping.domain.entity;

import shopping.exception.ImageLengthInvalidException;

import javax.persistence.Embeddable;

@Embeddable
public class Image {

    private static final int MAX_LENGTH = 255;

    private final String image;

    protected Image() {
        this.image = null;
    }

    public Image(final String value) {
        validateLength(value);

        this.image = value;
    }

    private void validateLength(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new ImageLengthInvalidException();
        }
    }

    public String getImage() {
        return image;
    }
}
