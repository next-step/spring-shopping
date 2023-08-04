package shopping.mart.domain;

import java.util.Objects;

public final class Product {

    private final Long id;
    private final Name name;
    private final ImageUrl imageUrl;
    private final Price price;

    public Product(final String name, final String imageUrl, final String price) {
        this(null, name, imageUrl, price);
    }

    public Product(final Long id, final String name, final String imageUrl, final String price) {
        this.id = id;
        this.name = new Name(name);
        this.imageUrl = new ImageUrl(imageUrl);
        this.price = new Price(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getImageUrl() {
        return imageUrl.getValue();
    }

    public String getPrice() {
        return price.getValue().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name)
                && Objects.equals(imageUrl, product.imageUrl) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }
}
