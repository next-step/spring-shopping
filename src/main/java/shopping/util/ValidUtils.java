package shopping.util;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import shopping.exception.WooWaException;

public class ValidUtils {

    private ValidUtils() {
    }

    public static void validNotNull(Object object) {
        if (Objects.isNull(object)) {
            throw new WooWaException(
                "입력 값이 있어야 합니다.",
                new IllegalArgumentException(),
                HttpStatus.BAD_REQUEST
            );
        }
    }

    public static void validNotEmpty(String string) {
        if (!StringUtils.hasText(string)) {
            throw new WooWaException(
                "입력 값이 있어야 합니다.",
                new IllegalArgumentException(),
                HttpStatus.BAD_REQUEST
            );
        }
    }

}
