package shopping.domain.cart;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "quantity", column = @Column(name = "quantity", nullable = false)))
    private Quantity quantity;

    protected CartItem() {
    }

    public CartItem(Long userId, Product product) {
        this(null, userId, product, new Quantity());
    }

    private CartItem(Long id, Long userId, Product product, Integer quantity) {
        this(id, userId, product, new Quantity(quantity));
    }

    private CartItem(Long id, Long userId, Product product, Quantity quantity) {
        this.id = id;
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public CartItem addQuantity() {
        return new CartItem(this.id, this.userId, this.product, this.quantity.addQuantity());
    }

    public CartItem updateQuantity(Integer quantity) {
        return new CartItem(this.id, this.userId, this.product, quantity);
    }

    public boolean isNotUserMatch(Long userId) {
        return !this.userId.equals(userId);
    }
}
