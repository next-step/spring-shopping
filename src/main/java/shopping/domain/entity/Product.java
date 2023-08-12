package shopping.domain.entity;

import shopping.domain.vo.Image;
import shopping.domain.vo.Name;
import shopping.domain.vo.Price;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private Name name;

    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "image"))
    private Image image;

    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "price"))
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

    public Name getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public Price getPrice() {
        return price;
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
}
