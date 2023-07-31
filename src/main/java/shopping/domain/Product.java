package shopping.domain;

import shopping.exception.ProductException;
import shopping.utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {

    public static final int MAX_NAME_LENGTH = 25;
    public static final int MIN_PRICE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = MAX_NAME_LENGTH, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private long price;

    public Product(String name, String imageUrl, Long price) {
        validateName(name);
        validateImageUrl(imageUrl);
        validatePrice(price);
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    private void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new ProductException("상품 이름이 존재하지 않습니다");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new ProductException("상품 이름은 최대 " + MAX_NAME_LENGTH + "자여야합니다");
        }
    }

    private void validatePrice(long price) {
        if (price < MIN_PRICE) {
            throw new ProductException("상품 가격은 " + MIN_PRICE + "이상이어야합니다");
        }
    }

    private void validateImageUrl(String imageUrl) {
        if (StringUtils.isBlank(imageUrl)) {
            throw new ProductException("상품 이미지가 존재하지 않습니다");
        }
    }

    protected Product() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
