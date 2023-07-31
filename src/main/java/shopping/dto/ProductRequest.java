package shopping.dto;

import org.springframework.util.Assert;

public class ProductRequest {

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
        Assert.notNull(name, "이름은 null 일수 없습니다.");
        Assert.isTrue(name.length() <= 255, "이름은 255자 이하여야 합니다.");
        Assert.isTrue(name.strip().length() > 0, "이름은 한글자 이상이어야 합니다.");

        Assert.notNull(imageUrl, "url은 null 일수 없습니다.");
        Assert.isTrue(imageUrl.length() <= 255, "url은 255자 이하여야 합니다.");
        Assert.isTrue(imageUrl.strip().length() > 0, "url은 한글자 이상이어야 합니다.");

        Assert.notNull(price, "가격은 null 일수 없습니다.");
        Assert.isTrue(price > 0L, "가격은 양수여야 합니다.");
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
