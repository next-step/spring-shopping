package shopping.domain.order;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Quantity;
import shopping.domain.product.Price;
import shopping.domain.product.ProductImage;
import shopping.domain.product.ProductName;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Embedded
    private Quantity quantity;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductImage image;

    @Embedded
    private Price price;

    protected OrderItem() {
    }

    public OrderItem(final CartItem cartItem) {
        this.quantity = cartItem.getQuantity();
        this.name = cartItem.getName();
        this.image = cartItem.getImage();
        this.price = cartItem.getPrice();
    }

    public Long getId() {
        return this.id;
    }

    public Order getOrder() {
        return this.order;
    }

    public Quantity getQuantity() {
        return this.quantity;
    }

    public ProductName getName() {
        return this.name;
    }

    public ProductImage getImage() {
        return this.image;
    }

    public Price getPrice() {
        return this.price;
    }

    public long getOrderItemTotalPrice() {
        return (long) this.price.getValue() * this.quantity.getValue();
    }

    void setOrder(final Order order) {
        this.order = order;
    }

}
