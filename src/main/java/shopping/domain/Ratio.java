package shopping.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import shopping.exception.ArgumentValidateFailException;

@Embeddable
public class Ratio {

    private static final double MIN_RATIO = 0.0;

    private Double ratio;

    protected Ratio() {
    }

    public Ratio(Double ratio) {
        validate(ratio);
        this.ratio = ratio;
    }

    private void validate(Double ratio) {
        if (ratio == null) {
            throw new ArgumentValidateFailException("환율은 null일 수 없습니다.");
        }
        if (ratio <= MIN_RATIO) {
            throw new ArgumentValidateFailException("환율은 0 이하일 수 없습니다.");
        }
    }

    public Double getRatio() {
        return ratio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ratio ratio1 = (Ratio) o;
        return Objects.equals(ratio, ratio1.ratio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratio);
    }
}
