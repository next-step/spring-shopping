package shopping.cart.domain;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import shopping.cart.domain.vo.Quantity;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.product.domain.Product;
import shopping.common.vo.Money;

@Entity
public class CartItem {

    private static final int DEFAULT_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long productId;
    private String productName;
    @Embedded
    private Money productPrice;
    @Embedded
    private Quantity quantity;

    protected CartItem() {
    }

    public CartItem(Long memberId, Long productId, String productName, Money productPrice,
        int quantity) {
        this.memberId = memberId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = new Quantity(quantity);
    }

    public CartItem(Long memberId, Long productId, String productName, Money productPrice) {
        this.memberId = memberId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = new Quantity(DEFAULT_QUANTITY);
    }

    public CartItem(Product product, Member member) {
        this(member.getId(), product.getId(), product.getName(), product.getPrice());
    }

    public boolean isProductChanged(Product product) {
        return !this.productName.equals(product.getName()) ||
            !this.productPrice.equals(product.getPrice());
    }

    public void updateQuantity(int quantity) {
        this.quantity = new Quantity(quantity);
    }

    public void increaseQuantity() {
        this.quantity = this.quantity.increase();
    }

    public void validateMyCartItem(Long memberId) {
        if (!Objects.equals(this.memberId, memberId)) {
            throw new WooWaException("본인의 장바구니가 아닙니다. memberId: '" + memberId + "' cartItemId: '"
                + this.id + "'", BAD_REQUEST);
        }
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

    public Quantity getQuantity() {
        return quantity;
    }
}
