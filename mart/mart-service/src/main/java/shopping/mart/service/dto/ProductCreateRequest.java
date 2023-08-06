package shopping.mart.service.dto;

public final class ProductCreateRequest {

    private String name;
    private String imageUrl;
    private String price;

    ProductCreateRequest() {
    }

    public ProductCreateRequest(final String name, final String imageUrl, final String price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }
}
