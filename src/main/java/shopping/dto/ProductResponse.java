package shopping.dto;

import shopping.domain.entity.Product;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final String image;
    private final int price;

    public ProductResponse(final Long id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductResponse of(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName().getName(),
                product.getImage().getImage(),
                product.getPrice().getPrice()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
