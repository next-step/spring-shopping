package shopping.product.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.common.vo.ImageStoreType;

@DisplayName("Image 단위 테스트")
class ImageTest {

    @Test
    @DisplayName("Image의 url을 반환한다.")
    void getUrl() {
        Image image = createLocalImage();

        String url = image.toUrl();

        Assertions.assertThat(url).isEqualTo("http://localhost:8080/image/name");
    }

    private static Image createLocalImage() {
        return new Image(ImageStoreType.LOCAL_STATIC, "name");
    }

}
