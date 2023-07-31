package shopping.dto;

import java.util.List;
import java.util.Objects;

public final class ProductsGetResponse {

    private final List<ProductElement> products;

    public ProductsGetResponse(final List<ProductElement> products) {
        this.products = products;
    }

    public List<ProductElement> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductsGetResponse)) {
            return false;
        }
        ProductsGetResponse that = (ProductsGetResponse) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }

    @Override
    public String toString() {
        return "ProductsGetResponse{" +
                "products=" + products +
                '}';
    }

    public static final class ProductElement {

        private final Long id;
        private final String name;
        private final String imageUrl;
        private final String price;

        public ProductElement(final Long id, final String name, final String imageUrl, final String price) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.price = price;
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

        public String getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ProductElement)) {
                return false;
            }
            ProductElement that = (ProductElement) o;
            return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                    && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(price, that.price);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, imageUrl, price);
        }

        @Override
        public String toString() {
            return "ProductElement{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }
    }
}