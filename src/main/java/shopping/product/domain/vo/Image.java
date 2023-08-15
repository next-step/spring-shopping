package shopping.product.domain.vo;

public class Image {

    private String url;

    protected Image() {
    }

    public Image(String url) {
        this.url = url;
    }

    public static Image from(String url) {
        return new Image(url);
    }

    public String toUrl() {
        return url;
    }
}
