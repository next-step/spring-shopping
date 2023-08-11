package shopping.domain.entity;

import shopping.exception.ImageLengthInvalidException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Image {

    private static final int MAX_LENGTH = 255;

    private final String value;

    protected Image() {
        this.value = null;
    }

    public Image(final String value) {
        validateLength(value);

        this.value = value;
    }

    private void validateLength(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new ImageLengthInvalidException();
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Image image = (Image) o;
        return Objects.equals(value, image.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
