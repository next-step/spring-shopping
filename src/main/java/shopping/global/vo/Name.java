package shopping.global.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.global.exception.ShoppingException;

@Embeddable
public class Name {

    private String name;

    protected Name() {
    }

    public Name(final String name) {
        validateIsNotNullOrEmpty(name);

        this.name = name;
    }

    private void validateIsNotNullOrEmpty(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new ShoppingException("상품 이름은 비어있거나 공백이면 안됩니다. 입력값: " + name);
        }
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Name that = (Name) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
