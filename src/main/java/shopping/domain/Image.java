package shopping.domain;

import java.util.Objects;

final class Image {

    private static final String DEFAULT_PRODUCT_IMAGE_URL = "/default-product.png";

    private final String value;

    Image(final String value) {
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
        if (!(o instanceof Image)) {
            return false;
        }
        Image image = (Image) o;
        return Objects.equals(value, image.value);
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
