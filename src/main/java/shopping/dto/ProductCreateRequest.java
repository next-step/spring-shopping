package shopping.dto;

import java.util.Objects;

public final class ProductCreateRequest {

    private final String name;
    private final String imageUrl;
    private final String price;

    public ProductCreateRequest(final String name, final String imageUrl, final String price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCreateRequest)) {
            return false;
        }
        ProductCreateRequest that = (ProductCreateRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(imageUrl, that.imageUrl)
                && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, imageUrl, price);
    }

    @Override
    public String toString() {
        return "ProductCreateRequest{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
