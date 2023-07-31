package shopping.domain;

import shopping.exception.ShoppingException;

public class ProductName {

    private final String value;

    public ProductName(final String value) {
        validateIsNotNullOrEmpty(value);

        this.value = value;
    }

    private void validateIsNotNullOrEmpty(final String value) {
        if (value == null || value.isBlank()) {
            throw new ShoppingException("상품 이름은 비어있거나 공백이면 안됩니다. 입력값: " + value);
        }
    }
}
