package shopping.domain.cart;


import shopping.domain.member.Member;
import shopping.domain.product.Product;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import javax.persistence.*;

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
    private CartItemQuantity cartItemQuantity;

    protected CartItem() {
    }

    public CartItem(final Member member, final Product product) {
        this(member, product, CartItemQuantity.defaultValue());
    }

    public CartItem(final Member member, final Product product, final CartItemQuantity cartItemQuantity) {
        this.member = member;
        this.product = product;
        this.cartItemQuantity = cartItemQuantity;
    }

    public void updateQuantity(final int quantity) {
        this.cartItemQuantity = CartItemQuantity.from(quantity);
    }

    public void plusQuantity() {
        this.cartItemQuantity = CartItemQuantity.from(this.getQuantity() + 1);
    }

    public void validateMember(final Member member) {
        if (!this.member.equals(member)) {
            throw new ShoppingException(ErrorCode.FORBIDDEN_MODIFY_CART_ITEM);
        }
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

    public int getQuantity() {
        return this.cartItemQuantity.getValue();
    }

}
