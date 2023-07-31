package shopping.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "product")
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
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
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
