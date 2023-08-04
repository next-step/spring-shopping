package shopping.mart.domain;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import shopping.core.exception.StatusCodeException;
import shopping.mart.domain.status.CartExceptionStatus;

public final class Cart {

    private final Long cartId;
    private final long userId;
    private final Map<Product, Integer> productCounts;

    public Cart(final Long cartId, final long userId) {
        this.cartId = cartId;
        this.userId = userId;
        this.productCounts = new HashMap<>();
    }

    public void addProduct(final Product product) {
        validateNullProduct(product);
        validateNotExistProduct(product);
        productCounts.put(product, productCounts.getOrDefault(product, 0) + 1);
    }

    private void validateNotExistProduct(final Product product) {
        if (productCounts.containsKey(product)) {
            throw new StatusCodeException(MessageFormat.format("product \"{0}\"가 이미 cart에 존재합니다.", product),
                    CartExceptionStatus.ALREADY_EXIST_PRODUCT.getStatus());
        }
    }

    public void updateProduct(final Product product, final int count) {
        validateNullProduct(product);
        validateCount(count);
        validateExistProduct(product);
        productCounts.put(product, count);
    }

    public void deleteProduct(Product product) {
        validateNullProduct(product);
        validateExistProduct(product);
        productCounts.remove(product);
    }

    private void validateNullProduct(final Product product) {
        if (product == null) {
            throw new IllegalStateException("product는 null이 될 수 없습니다");
        }
    }

    private void validateCount(final int count) {
        if (count <= 0) {
            throw new StatusCodeException(MessageFormat.format("count\"{0}\"는 0 이하가 될 수 없습니다.", count),
                    CartExceptionStatus.UPDATE_COUNT_NOT_POSITIVE.getStatus());
        }
    }

    private void validateExistProduct(final Product product) {
        if (productCounts.containsKey(product)) {
            return;
        }
        throw new StatusCodeException(MessageFormat.format("update할 product\"{0}\"를 찾을 수 없습니다.", product),
                CartExceptionStatus.NOT_EXIST_PRODUCT.getStatus());
    }

    public Long getCartId() {
        return cartId;
    }

    public long getUserId() {
        return userId;
    }

    public Map<Product, Integer> getProductCounts() {
        return productCounts;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", productCounts=" + productCounts +
                '}';
    }
}
