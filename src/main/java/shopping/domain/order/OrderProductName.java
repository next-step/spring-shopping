package shopping.domain.order;

import static shopping.exception.OrderExceptionType.NO_CONTENT_NAME;

import java.util.Objects;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.exception.ShoppingException;

@Embeddable
public class OrderProductName {

    private String name;

    protected OrderProductName() {
    }

    public OrderProductName(final String name) {
        validateIsNotNullOrEmpty(name);

        this.name = name;
    }

    private void validateIsNotNullOrEmpty(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new ShoppingException(NO_CONTENT_NAME, name);
        }
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderProductName that = (OrderProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
