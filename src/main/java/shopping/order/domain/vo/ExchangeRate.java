package shopping.order.domain.vo;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.http.HttpStatus;
import shopping.exception.WooWaException;

@Embeddable
public class ExchangeRate {

    @Column(name = "exchangeRate")
    private BigDecimal value;

    protected ExchangeRate() {
    }

    public ExchangeRate(BigDecimal value) {
        validateExchangeRate(value);
        this.value = new BigDecimal(String.valueOf(value));
    }

    private void validateExchangeRate(BigDecimal exchangeRate) {
        if (exchangeRate.compareTo(new BigDecimal("0")) < 0) {
            throw new WooWaException("환율이 음수일 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public BigDecimal getValue() {
        return value;
    }
}
