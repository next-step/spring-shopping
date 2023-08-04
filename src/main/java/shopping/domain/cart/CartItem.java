package shopping.domain.cart;

import shopping.domain.user.User;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {

    private static final int ADD_COUNT = 1;

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

    public CartItem(Long id, User user, Product product, Integer quantity) {
        this(user, product);
        this.id = id;
        this.quantity = new Quantity(quantity);
    }

    public CartItem(User user, Product product) {
        this.user = user;
        this.product = product;
        this.quantity = new Quantity();
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
        return new CartItem(this.id, this.user, this.product, this.getQuantity() + ADD_COUNT);
    }

    public CartItem updateQuantity(Integer quantity) {
        return new CartItem(this.id, this.user, this.product, quantity);
    }

    public boolean isNotUserMatch(User user) {
        return !this.user.isSame(user);
    }
}
