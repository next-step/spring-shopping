package shopping.domain.cart;

import static shopping.exception.ShoppingErrorType.FORBIDDEN_MODIFY_CART_ITEM;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import shopping.domain.member.Member;
import shopping.domain.product.Price;
import shopping.domain.product.Product;
import shopping.domain.product.ProductImage;
import shopping.domain.product.ProductName;
import shopping.exception.ShoppingException;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Embedded
    private Quantity quantity;

    protected CartItem() {
    }

    public CartItem(final Member member, final Product product) {
        this(member, product, Quantity.defaultValue());
    }

    public CartItem(final Member member, final Product product, final Quantity quantity) {
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public void updateQuantity(final int quantity) {
        this.quantity = Quantity.from(quantity);
    }

    public void plusQuantity() {
        this.quantity = this.quantity.plus(1);
    }

    public boolean validateMember(final Member member) {
        if (!this.member.equals(member)) {
            throw new ShoppingException(FORBIDDEN_MODIFY_CART_ITEM);
        }
        return true;
    }

    public Long getId() {
        return this.id;
    }

    public Member getMember() {
        return this.member;
    }

    public Product getProduct() {
        return this.product;
    }

    public Quantity getQuantity() {
        return this.quantity;
    }

    public ProductName getName() {
        return this.product.getName();
    }

    public ProductImage getImage() {
        return this.product.getImage();
    }

    public Price getPrice() {
        return this.product.getPrice();
    }

}
