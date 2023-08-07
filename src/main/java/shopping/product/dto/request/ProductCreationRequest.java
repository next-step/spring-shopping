package shopping.product.dto.request;

import shopping.common.vo.Image;
import shopping.product.domain.Product;

public class ProductCreationRequest {

    private String name;
    private String price;
    private String imageUrl;

    private ProductCreationRequest() {
    }

    public ProductCreationRequest(String name, String price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toEntity() {
        return new Product(name, new Image(imageUrl), price);
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
