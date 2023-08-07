package shopping.mart.repository.entity;

import java.io.Serializable;
import java.util.Objects;

public class CartProductIdEntity implements Serializable {

    private CartEntity cartEntity;
    private Long productId;

    private CartProductIdEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartProductIdEntity)) {
            return false;
        }
        CartProductIdEntity that = (CartProductIdEntity) o;
        return Objects.equals(cartEntity.getId(), that.cartEntity.getId()) && Objects.equals(
            productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartEntity.getId(), productId);
    }
}
