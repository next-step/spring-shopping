package shopping.dto;

import java.util.Objects;

public final class CartUpdateRequest {

    private long productId;
    private int count;

    CartUpdateRequest() {
    }

    public CartUpdateRequest(long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartUpdateRequest)) {
            return false;
        }
        CartUpdateRequest that = (CartUpdateRequest) o;
        return productId == that.productId && count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, count);
    }

    @Override
    public String toString() {
        return "CartUpdateRequest{" +
                "productId=" + productId +
                ", count=" + count +
                '}';
    }
}
