package shopping.mart.repository.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_product")
@IdClass(CartProductIdEntity.class)
public class CartProductEntity extends TimeBaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    @Id
    @JoinColumn(name = "product_id")
    private Long productId;

    @Column(name = "count", nullable = false)
    private Integer count;

    private CartProductEntity() {
    }

    public CartProductEntity(CartEntity cartEntity, Long productId, Integer count) {
        this.cartEntity = cartEntity;
        this.productId = productId;
        this.count = count;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }

    public void increaseCount() {
        this.count++;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartProductEntity)) {
            return false;
        }
        CartProductEntity that = (CartProductEntity) o;
        return Objects.equals(cartEntity.getId(), that.cartEntity.getId()) && Objects.equals(
            productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartEntity.getId(), productId);
    }
}
