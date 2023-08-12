package shopping.product.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.http.HttpStatus;
import shopping.common.vo.Image;
import shopping.exception.WooWaException;
import shopping.common.vo.Money;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    private Image image;
    @Embedded
    private Money price;

    protected Product() {
    }

    public Product(String name, Image image, Money price) {
        validateNotNegative(price);
        this.name = name;
        this.image = image;
        this.price = price;
    }

    private void validateNotNegative(Money price) {
        if (price.isSmallerThan(new Money("0"))) {
            throw new WooWaException(
                "상품의 가격은 0원보다 작을 수 없습니다.",
                new IllegalArgumentException(),
                HttpStatus.BAD_REQUEST
            );
        }
    }

    public Product(String name, Image image, String price) {
        this(name, image, new Money(price));
    }

    public Product(String name, String imageUrl, String price) {
        this(name, new Image(imageUrl), new Money(price));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public Money getPrice() {
        return price;
    }

    public void update(String name, String price, String imageUrl) {
        this.name = name;
        this.price = new Money(price);
        this.image = new Image(imageUrl);
    }
}
