package shopping.domain;

import javax.persistence.Embeddable;
import shopping.exception.ShoppingException;

@Embeddable
public class ProductName {

    private String name;

    protected ProductName() {
    }

    public ProductName(final String name) {
        validateIsNotNullOrEmpty(name);

        this.name = name;
    }

    private void validateIsNotNullOrEmpty(final String value) {
        if (value == null || value.isBlank()) {
            throw new ShoppingException("상품 이름은 비어있거나 공백이면 안됩니다. 입력값: " + value);
        }
    }

    public String getName() {
        return this.name;
    }
}
