package shopping.order.domain;

import javax.persistence.*;

import shopping.common.domain.Quantity;
import shopping.common.converter.MoneyConverter;
import shopping.common.converter.QuantityConverter;
import shopping.common.domain.Money;

@Embeddable
public class OrderItem {

    private Long productId;
    private String name;
    @Convert(converter = MoneyConverter.class)
    private Money price;
    @Convert(converter = QuantityConverter.class)
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
