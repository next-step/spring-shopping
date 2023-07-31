package shopping.product.dto.request;

import shopping.common.vo.Image;
import shopping.product.domain.Product;

public class ProductCreationRequest {

    private String name;
    private String price;

    private ProductCreationRequest() {
    }

    public Product toEntity(Image productImage) {
        return new Product(name, productImage, price);
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
