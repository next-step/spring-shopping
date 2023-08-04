package shopping.mart.persist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_product")
public class CartProductEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @Column(name = "count", nullable = false)
    private Integer count;

    public CartProductEntity() {
    }

    public CartProductEntity(Long id, CartEntity cartEntity, ProductEntity productEntity, Integer count) {
        this.id = id;
        this.cartEntity = cartEntity;
        this.productEntity = productEntity;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
