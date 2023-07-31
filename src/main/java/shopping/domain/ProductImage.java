package shopping.domain;

import javax.persistence.Embeddable;
import shopping.exception.ShoppingException;

@Embeddable
public class ProductImage {

    private String image;

    protected ProductImage() {
    }

    public ProductImage(final String image) {
        validateIsNotNullOrEmpty(image);

        this.image = image;
    }

    private void validateIsNotNullOrEmpty(final String value) {
        if (value == null || value.isBlank()) {
            throw new ShoppingException("상품 이미지는 비어있거나 공백이면 안됩니다. 입력값: " + value);
        }
    }

    public String getImage() {
        return this.image;
    }
}
