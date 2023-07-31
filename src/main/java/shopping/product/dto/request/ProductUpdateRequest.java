package shopping.product.dto.request;

public class ProductUpdateRequest {

    private Long id;
    private String name;
    private String price;

    private ProductUpdateRequest() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
