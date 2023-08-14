package shopping.cart.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import shopping.global.vo.Quantity;
import shopping.product.domain.Product;

@Entity
@Table(name = "CART_PRODUCT")
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Embedded
    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity quantity = new Quantity(1);

    protected CartProduct() {
    }

    public CartProduct(
        final Long memberId,
        final Product product
    ) {
        this.memberId = memberId;
        this.product = product;
    }

    public void updateQuantity(final int quantity) {
        this.quantity = new Quantity(quantity);
    }

    public Long getId() {
        return this.id;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity.getValue();
    }
}
