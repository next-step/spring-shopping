package shopping.common.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Image {

    @Column
    private String imagePath;

    protected Image() {
    }

    public Image(ImageStoreType imageStoreType, String imageName) {
        this.imagePath = imageStoreType.getPath() + imageName;
    }

    public String getImagePath() {
        return imagePath;
    }
}
