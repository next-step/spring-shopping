package shopping.dto;

import java.util.Objects;

public final class CartAddRequest {

    private Long productId;

    CartAddRequest() {
    }

    public CartAddRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartAddRequest)) {
            return false;
        }
        CartAddRequest that = (CartAddRequest) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "CartAddRequest{" +
                "productId=" + productId +
                '}';
    }
}
