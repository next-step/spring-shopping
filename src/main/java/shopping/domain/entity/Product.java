package shopping.domain.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private Name name;

    @Column(nullable = false)
    private Image image;

    @Column
    private Price price;

    protected Product() {
    }

    public Product(final Long id, final Name name, final Image image, final Price price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getImage() {
        return image.getImage();
    }

    public int getPrice() {
        return price.getPrice();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean equalsById(final long id) {
        return Objects.equals(this.id, id);
    }
}
