package shopping.order.domain;

import javax.persistence.*;

import shopping.common.converter.ImageConverter;
import shopping.common.domain.Quantity;
import shopping.common.converter.MoneyConverter;
import shopping.common.converter.QuantityConverter;
import shopping.common.domain.Money;
import shopping.product.domain.vo.Image;

@Embeddable
public class OrderItem {

    private Long productId;
    private String name;
    @Convert(converter = MoneyConverter.class)
    private Money price;
    @Convert(converter = QuantityConverter.class)
    private Quantity quantity;
    @Convert(converter = ImageConverter.class)
    private Image image;

    public OrderItem(Long productId, String name, Money price, Quantity quantity, Image image) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
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

    public Image getImage() {
        return image;
    }
}
