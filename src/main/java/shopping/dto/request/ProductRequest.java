package shopping.dto.request;

import static shopping.dto.request.validator.RequestArgumentValidator.validateNumberNotNullAndPositive;
import static shopping.dto.request.validator.RequestArgumentValidator.validateStringNotNullAndNotBlankAndNotLong;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductRequest {

    private static final int MAX_NAME_LENGTH = 255;
    private static final String NAME_NAME = "name";
    private static final String IMAGE_URL_NAME = "imageUrl";
    private static final String PRICE_NAME = "price";

    private final String name;
    private final String imageUrl;
    private final Long price;

    @JsonCreator
    public ProductRequest(@JsonProperty(NAME_NAME) final String name,
            @JsonProperty(IMAGE_URL_NAME) final String imageUrl,
            @JsonProperty(PRICE_NAME) final Long price) {
        validate(name, imageUrl, price);
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    private void validate(String name, String imageUrl, Long price) {
        validateStringNotNullAndNotBlankAndNotLong(name, NAME_NAME, MAX_NAME_LENGTH);
        validateStringNotNullAndNotBlankAndNotLong(imageUrl, IMAGE_URL_NAME, MAX_NAME_LENGTH);
        validateNumberNotNullAndPositive(price, PRICE_NAME);
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
