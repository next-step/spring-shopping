package shopping.cart.dto.response;

import shopping.cart.domain.entity.Product;

public final class ProductResponse {

    private Long id;
    private String name;
    private String imageUuidFileName;
    private int price;

    private ProductResponse(final Long id, final String name, final String imageUuid,
        final int price) {
        this.id = id;
        this.name = name;
        this.imageUuidFileName = imageUuid;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getImageUuidFileName(),
            product.getPrice()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUuidFileName() {
        return imageUuidFileName;
    }

    public int getPrice() {
        return price;
    }
}
