package shopping.common.converter;

import shopping.product.domain.vo.Image;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImageConverter implements AttributeConverter<Image, String> {
    @Override
    public String convertToDatabaseColumn(Image image) {
        return image.toUrl();
    }

    @Override
    public Image convertToEntityAttribute(String url) {
        return new Image(url);
    }
}
