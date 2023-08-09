package shopping.domain.order;

import shopping.domain.wrapper.Image;
import shopping.domain.wrapper.Name;
import shopping.domain.wrapper.Price;
import shopping.domain.wrapper.Quantity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class OrderItem {

    private Long productId;
    @Column(name = "name")
    private Name name;

    @Column(name = "image")
    private Image image;

    @Column(name = "price")
    private Price price;

    @Embedded
    @Column(name = "quantity")
    private Quantity quantity;

    protected OrderItem() {

    }

    public OrderItem(final Long productId,
                     final Name name,
                     final Image image,
                     final Price price,
                     final Quantity quantity) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
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
