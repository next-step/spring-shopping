package shopping.domain.cart;

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
import shopping.domain.product.Product;

@Entity
@Table(name = "cart_products")
public class CartProduct {

    private static final CartProductQuantity DEFAULT_QUANTITY = new CartProductQuantity(1);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private CartProductQuantity quantity = DEFAULT_QUANTITY;

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
        this.quantity = new CartProductQuantity(quantity);
    }

    public Long getId() {
        return this.id;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getProductImage() {
        return product.getImage();
    }

    public String getProductName() {
        return product.getName();
    }

    public int getProductPrice() {
        return product.getPrice();
    }

    public int getQuantity() {
        return this.quantity.getValue();
    }
}
