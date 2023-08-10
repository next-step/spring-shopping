package shopping.order.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import shopping.cart.domain.vo.Quantity;
import shopping.product.domain.vo.Money;

@Embeddable
public class OrderItem {

    private Long productId;
    private String name;
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "price"))
    private Money price;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity quantity;
    private String imageUrl;

    public OrderItem(Long productId, String name, Money price, Quantity quantity, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    protected OrderItem() {
    }

    public Money getTotalPrice() {
        return price.multiply(quantity.getValue());
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
