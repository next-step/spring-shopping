package shopping.infrastructure;

import java.time.LocalDateTime;

public interface ExchangeRateApi {
    double getExchangeRateEveryMinute(final LocalDateTime now);
}
