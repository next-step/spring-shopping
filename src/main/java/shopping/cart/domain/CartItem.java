package shopping.cart.domain;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Objects;
import javax.persistence.*;

import org.springframework.context.annotation.Configuration;
import shopping.common.converter.ImageConverter;
import shopping.common.domain.Quantity;
import shopping.common.converter.MoneyConverter;
import shopping.common.converter.QuantityConverter;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.product.domain.Product;
import shopping.common.domain.Money;
import shopping.product.domain.vo.Image;

@Entity
@Configuration
public class CartItem {

    private static final int DEFAULT_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long productId;
    private String productName;
    @Convert(converter = MoneyConverter.class)
    private Money productPrice;
    @Convert(converter = QuantityConverter.class)
    private Quantity quantity;
    @Convert(converter = ImageConverter.class)
    private Image image;

    protected CartItem() {
    }

    public CartItem(Long memberId, Long productId, String productName, Money productPrice,
        Quantity quantity, Image image) {
        this.memberId = memberId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.image = image;
    }
    public CartItem(Product product, Member member, int quantity) {
        this(member.getId(),
                product.getId(),
                product.getName(),
                product.getPrice(),
                new Quantity(quantity),
                product.getImage()
        );
    }

    public CartItem(Product product, Member member) {
        this(member.getId(),
             product.getId(),
             product.getName(),
             product.getPrice(),
             new Quantity(DEFAULT_QUANTITY),
             product.getImage()
        );
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

    public Image getImage() {
        return image;
    }
}
