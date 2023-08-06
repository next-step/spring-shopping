package shopping.dto.response;

import shopping.domain.entity.ProductEntity;

public class ProductResponse {

    private Long id;
    private String name;
    private String imageFileName;
    private int price;

    private ProductResponse(final Long id, final String name, final String imageUuid,
        final int price) {
        this.id = id;
        this.name = name;
        this.imageFileName = imageUuid;
        this.price = price;
    }

    public static ProductResponse from(ProductEntity productEntity) {
        return new ProductResponse(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getImageFileName(),
            productEntity.getPrice()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public int getPrice() {
        return price;
    }
}
