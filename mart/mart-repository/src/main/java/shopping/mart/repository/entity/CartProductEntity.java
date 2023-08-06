package shopping.mart.repository.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import shopping.mart.domain.Product;

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

    public Long getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isMatchedProduct(Product product) {
        return Objects.equals(product.getId(), productId);
    }
}
