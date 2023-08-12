package shopping.domain.entity;

import shopping.domain.vo.Quantity;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity quantity;

    protected CartItem() {
    }

    public CartItem(final Long id, final User user, final Product product, final Quantity quantity) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(final User user, final Product product, final Quantity quantity) {
        this(null, user, product, quantity);
    }

    public void increaseQuantity() {
        quantity = quantity.increase();
    }

    public void updateQuantity(final Quantity quantity) {
        this.quantity = quantity;
    }

    public boolean checkSameProduct(final CartItem other) {
        return this.product.equals(other.product);
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

    public Quantity getQuantity() {
        return quantity;
    }

    public boolean equalsById(final long id) {
        return this.id == id;
    }
}
