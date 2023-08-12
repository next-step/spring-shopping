package shopping.domain.entity;

import shopping.domain.vo.Image;
import shopping.domain.vo.Name;
import shopping.domain.vo.Price;
import shopping.domain.vo.Quantity;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long productId;

    @Column(nullable = false, length = 20)
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private Name name;

    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "image"))
    private Image image;

    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private Price price;

    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity quantity;

    protected OrderItem() {
    }

    public OrderItem(final Long id,
                     final Long productId,
                     final Name name,
                     final Image image,
                     final Price price,
                     final Quantity quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem(final Long productId,
                     final Name name,
                     final Image image,
                     final Price price,
                     final Quantity quantity) {
        this(null, productId, name, image, price, quantity);
    }

    public Price calculateTotalPrice() {
        return price.multiply(quantity.getValue());
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Name getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
