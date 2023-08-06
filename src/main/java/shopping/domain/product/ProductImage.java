package shopping.domain.product;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

@Embeddable
public class ProductImage {

    private static final int MAX_IMAGE_LENGTH = 255;

    @Column(name = "image", nullable = false)
    private String value;

    protected ProductImage() {
    }

    private ProductImage(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (!StringUtils.hasText(value) || value.length() > MAX_IMAGE_LENGTH) {
            throw new ShoppingException(ErrorCode.PRODUCT_IMAGE_INVALID);
        }
    }

    public static ProductImage from(final String value) {
        return new ProductImage(value);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductImage productImage = (ProductImage) o;
        return Objects.equals(value, productImage.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
