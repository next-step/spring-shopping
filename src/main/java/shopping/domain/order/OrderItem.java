package shopping.domain.order;

import shopping.domain.cart.Quantity;
import shopping.domain.product.Product;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderItem {

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @Embedded
    @Column(name = "quantity")
    private Quantity quantity;

    protected OrderItem() {

    }

    public OrderItem(Product product, Quantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
