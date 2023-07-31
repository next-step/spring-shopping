package shopping.dto.response;

import shopping.domain.entity.ProductEntity;

public class ProductResponse {

    private Long id;
    private String name;
    private String imageUuid;
    private int price;

    private ProductResponse(final Long id, final String name, final String imageUuid,
        final int price) {
        this.id = id;
        this.name = name;
        this.imageUuid = imageUuid;
        this.price = price;
    }

    public static ProductResponse of(ProductEntity productEntity) {
        return new ProductResponse(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getImageUuid(),
            productEntity.getPrice()
        );
    }
}
