package shopping.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    private Quantity quantity;

    protected CartItem() {
    }

    public CartItem(Long id, User user, Product product, int quantity) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.quantity = new Quantity(quantity);
    }

    public CartItem(User user, Product product, int quantity) {
        this(null, user, product, quantity);
    }

    public void increaseQuantity() {
        quantity = quantity.increase();
    }

    public void updateQuantity(int quantity) {
        this.quantity = this.quantity.update(quantity);
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

    public int getQuantity() {
        return quantity.getValue();
    }


}
