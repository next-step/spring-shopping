package shopping.dto.response;

import shopping.domain.entity.ProductEntity;

public class ProductResponse {

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

    public static ProductResponse from(ProductEntity productEntity) {
        return new ProductResponse(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getImageUuidFileName(),
            productEntity.getPrice()
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
