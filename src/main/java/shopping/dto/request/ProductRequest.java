package shopping.dto.request;

import static shopping.dto.request.validator.RequestArgumentValidator.validateNumberArgument;
import static shopping.dto.request.validator.RequestArgumentValidator.validateStringArgument;

public class ProductRequest {

    private static final int MAX_NAME_LENGTH = 255;

    private final String name;
    private final String imageUrl;
    private final Long price;

    public ProductRequest(String name, String imageUrl, Long price) {
        validate(name, imageUrl, price);
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    private void validate(String name, String imageUrl, Long price) {
        validateStringArgument(name, "이름", MAX_NAME_LENGTH);
        validateStringArgument(imageUrl, "이미지 URL", MAX_NAME_LENGTH);
        validateNumberArgument(price, "가격");
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getPrice() {
        return price;
    }
}
