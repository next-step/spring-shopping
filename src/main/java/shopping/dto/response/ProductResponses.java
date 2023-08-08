package shopping.dto.response;

import java.util.List;

public class ProductResponses {

    private final List<ProductResponse> products;

    private ProductResponses(final List<ProductResponse> productResponses) {
        this.products = productResponses;
    }

    public static ProductResponses from(final List<ProductResponse> productResponses) {
        return new ProductResponses(productResponses);
    }

    public List<ProductResponse> getProducts() {
        return this.products;
    }
}
