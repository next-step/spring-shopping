package shopping.dto.response;

import shopping.domain.cart.Product;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Long price;

    private ProductResponse(Long id, String name, String imageUrl, Long price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getPrice()
        );
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
}
