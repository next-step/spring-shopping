package shopping.service;

import java.time.LocalDateTime;

public interface ExchangeRateService {
    double getExchangeRateEveryMinute(final LocalDateTime now);
}
