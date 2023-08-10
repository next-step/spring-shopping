package shopping.domain;

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
}
