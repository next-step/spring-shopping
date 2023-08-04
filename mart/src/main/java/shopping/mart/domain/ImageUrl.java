package shopping.mart.domain;

import java.util.Objects;

final class ImageUrl {

    private static final String DEFAULT_PRODUCT_IMAGE_URL = "images/default-product.png";

    private final String value;

    ImageUrl(final String value) {
        this.value = getDefaultValueIfIsNullUrl(value);
    }

    private String getDefaultValueIfIsNullUrl(final String value) {
        return value == null || value.isBlank() ? DEFAULT_PRODUCT_IMAGE_URL : value;
    }

    String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageUrl)) {
            return false;
        }
        ImageUrl imageUrl = (ImageUrl) o;
        return Objects.equals(value, imageUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Image{" +
                "value='" + value + '\'' +
                '}';
    }
}
