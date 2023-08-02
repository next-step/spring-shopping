package shopping.cart.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import shopping.product.domain.Product;
import shopping.product.domain.vo.Money;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long productId;
    private String productName;
    private Money productPrice;
    private int quantity;

    protected CartItem() {
    }

    public CartItem(Long memberId, Long productId, String productName, Money productPrice,
        int quantity) {
        this.memberId = memberId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public boolean isProductChanged(Product product) {
        return !this.productName.equals(product.getName()) ||
            !this.productPrice.equals(product.getPrice());
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Money getProductPrice() {
        return productPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
