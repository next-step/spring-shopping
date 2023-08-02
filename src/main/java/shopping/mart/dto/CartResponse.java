package shopping.mart.dto;

import java.util.List;
import java.util.Objects;

public class CartResponse {

    private long cartId;
    private List<ProductResponse> productResponses;

    CartResponse() {
    }

    public CartResponse(long cartId, List<ProductResponse> productResponses) {
        this.cartId = cartId;
        this.productResponses = productResponses;
    }

    public long getCartId() {
        return cartId;
    }

    public List<ProductResponse> getProductResponses() {
        return productResponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartResponse)) {
            return false;
        }
        CartResponse that = (CartResponse) o;
        return cartId == that.cartId && Objects.equals(productResponses, that.productResponses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productResponses);
    }

    @Override
    public String toString() {
        return "CartResponse{" +
                "cartId=" + cartId +
                ", productResponses=" + productResponses +
                '}';
    }

    public static final class ProductResponse {
        private long id;
        private int count;
        private String imageUrl;
        private String name;

        public ProductResponse() {
        }

        public ProductResponse(final long id, final int count, final String imageUrl, final String name) {
            this.id = id;
            this.count = count;
            this.imageUrl = imageUrl;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public int getCount() {
            return count;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ProductResponse)) {
                return false;
            }
            ProductResponse that = (ProductResponse) o;
            return Objects.equals(id, that.id) && Objects.equals(count, that.count)
                    && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, count, imageUrl, name);
        }

        @Override
        public String toString() {
            return "ProductResponse{" +
                    "id='" + id + '\'' +
                    ", count='" + count + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
