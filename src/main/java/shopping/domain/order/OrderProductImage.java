package shopping.domain.order;

import static shopping.exception.OrderExceptionType.NO_CONTENT_IMAGE;

import java.util.Objects;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.exception.ShoppingException;

@Embeddable
public class OrderProductImage {

    private String image;

    protected OrderProductImage() {
    }

    public OrderProductImage(final String image) {
        validateIsNotNullOrEmpty(image);

        this.image = image;
    }

    private void validateIsNotNullOrEmpty(final String image) {
        if (!StringUtils.hasText(image)) {
            throw new ShoppingException(NO_CONTENT_IMAGE, image);
        }
    }

    public String getImage() {
        return this.image;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderProductImage that = (OrderProductImage) o;
        return Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image);
    }
}