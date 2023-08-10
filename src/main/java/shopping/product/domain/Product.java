package shopping.product.domain;

import javax.persistence.*;

import org.springframework.http.HttpStatus;
import shopping.common.converter.MoneyConverter;
import shopping.common.vo.ImageStoreType;
import shopping.exception.WooWaException;
import shopping.product.domain.vo.Image;
import shopping.common.domain.Money;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    private Image image;
    @Convert(converter = MoneyConverter.class)
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
        this(name, new Image(ImageStoreType.NONE, imageUrl), new Money(price));
    }

    public Product(Long id, String name, String image, String price) {
        this(name, image, price);
        this.id = id;
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

    public void updateValues(String name, String price, String imageUrl) {
        this.name = name;
        this.price = new Money(price);
        this.image = new Image(ImageStoreType.NONE, imageUrl);
    }
}
