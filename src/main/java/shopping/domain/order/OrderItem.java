package shopping.domain.order;

import shopping.domain.cart.Quantity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class OrderItem {

    private Long productId;
    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private int price;

    @Embedded
    @Column(name = "quantity")
    private Quantity quantity;

    protected OrderItem() {

    }

    public OrderItem(Long productId, String name, String image, int price, Quantity quantity) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
