package shopping.product.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.product.domain.Product;

public class ProductResponse {
    private Long id;
    private String name;
    private String url;
    private String price;

    private ProductResponse() {
    }

    public ProductResponse(Long id, String name, String url, String price) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getImage().toUrl(),
            product.getPrice().getAmount().toPlainString()
        );
    }

    public static List<ProductResponse> listOf(List<Product> products) {
        return products.stream()
            .map(ProductResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getPrice() {
        return price;
    }
}
