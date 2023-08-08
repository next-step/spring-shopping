package shopping.dto.response;

import shopping.domain.product.Product;

public class ProductResponse {

    private final Long id;
    private final String image;
    private final String name;
    private final int price;

    private ProductResponse(final Long id, final String image, final String name, final int price) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse from(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getImage(),
                product.getName(),
                product.getPrice()
        );
    }

    public Long getId() {
        return this.id;
    }

    public String getImage() {
        return this.image;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }
}
