package shopping.domain.product;

import static shopping.exception.ShoppingErrorType.PRODUCT_NAME_INVALID;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.exception.ShoppingException;

@Embeddable
public class ProductName {

    private static final int MAX_PRODUCT_NAME_LENGTH = 30;

    @Column(name = "name", nullable = false, length = MAX_PRODUCT_NAME_LENGTH)
    private String value;

    protected ProductName() {
    }

    private ProductName(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (!StringUtils.hasText(value) || value.length() > MAX_PRODUCT_NAME_LENGTH) {
            throw new ShoppingException(PRODUCT_NAME_INVALID);
        }
    }

    public static ProductName from(final String value) {
        return new ProductName(value);
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
        final ProductName productName = (ProductName) o;
        return Objects.equals(value, productName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
