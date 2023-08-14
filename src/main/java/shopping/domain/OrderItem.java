package shopping.domain;

import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_product_id")
    private Product originalProduct;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "price", column = @Column(name = "price", nullable = false)))
    private Price price;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "quantity", column = @Column(name = "quantity", nullable = false)))
    private Quantity quantity;

    protected OrderItem() {
    }

    public OrderItem(
            Order order,
            Product originalProduct,
            String name,
            String imageUrl,
            Price price,
            Quantity quantity) {

        this.order = order;
        this.originalProduct = originalProduct;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem(
            Order order,
            CartItem cartItem) {

        this.order = order;
        this.originalProduct = cartItem.getProduct();
        this.name = this.originalProduct.getName();
        this.imageUrl = this.originalProduct.getImageUrl();
        this.price = new Price(this.originalProduct.getPrice());
        this.quantity = new Quantity(cartItem.getQuantity());
    }

    public OrderItem(
            Long id,
            Order order,
            Product originalProduct,
            String name,
            String imageUrl,
            Price price,
            Quantity quantity) {

        this(order, originalProduct, name, imageUrl, price, quantity);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getOriginalProduct() {
        return originalProduct;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getPrice() {
        return price.getPrice();
    }

    public Integer getQuantity() {
        return quantity.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
