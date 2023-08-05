package shopping.domain.cart;

import shopping.domain.user.User;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "quantity", column = @Column(name = "quantity", nullable = false)))
    private Quantity quantity;

    protected CartItem() {
    }

    public CartItem(User user, Product product) {
        this(null, user, product, new Quantity());
    }

    private CartItem(Long id, User user, Product product, Integer quantity) {
        this(id, user, product, new Quantity(quantity));
    }

    private CartItem(Long id, User user, Product product, Quantity quantity) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity.getQuantity();
    }

    public CartItem addQuantity() {
        return new CartItem(this.id, this.user, this.product, this.quantity.addQuantity());
    }

    public CartItem updateQuantity(Integer quantity) {
        return new CartItem(this.id, this.user, this.product, quantity);
    }

    public boolean isNotUserMatch(User user) {
        return !this.user.isSame(user);
    }
}
