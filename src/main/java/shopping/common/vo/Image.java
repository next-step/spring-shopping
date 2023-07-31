package shopping.common.vo;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Image {

    @Enumerated(value = EnumType.STRING)
    private ImageStoreType imageStoreType;
    private String fileName;

    protected Image() {
    }

    public Image(ImageStoreType imageStoreType, String fileName) {
        this.imageStoreType = imageStoreType;
        this.fileName = fileName;
    }

    public String toUrl() {
        return imageStoreType.getPath() + fileName;
    }

    public ImageStoreType getImageStoreType() {
        return imageStoreType;
    }

    public String getFileName() {
        return fileName;
    }
}
