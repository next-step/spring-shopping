package shopping.domain.wrapper;

import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
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
            throw new ShoppingException(ErrorType.IMAGE_TOO_LONG);
        }
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image1 = (Image) o;
        return Objects.equals(image, image1.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image);
    }
}
