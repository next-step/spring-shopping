package shopping.product.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.http.HttpStatus;
import shopping.exception.WooWaException;
import shopping.product.domain.vo.Money;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imagePath;
    @Embedded
    private Money price;

    protected Product() {
    }

    public Product(String name, String imagePath, Money price) {
        validateNotNegative(price);
        this.name = name;
        this.imagePath = imagePath;
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

    public Product(String name, String imagePath, String price) {
        this(name, imagePath, new Money(price));
    }
}
