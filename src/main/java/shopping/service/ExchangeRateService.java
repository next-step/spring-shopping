package shopping.service;

import java.time.LocalDateTime;

public interface ExchangeRateService {
    double getExchangeRate(final LocalDateTime now);
}
