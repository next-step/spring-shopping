package shopping.common.vo;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Image {

    @Enumerated(value = EnumType.STRING)
    private ImageStoreType imageStoreType;
    private String filePath;
    private String fileName;

    protected Image() {
    }

    public Image(ImageStoreType imageStoreType, String filePath, String fileName) {
        this.imageStoreType = imageStoreType;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public ImageStoreType getImageStoreType() {
        return imageStoreType;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }
}
