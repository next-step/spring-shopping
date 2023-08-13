package shopping.infrastructure;

import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import java.time.Duration;
import java.time.LocalDateTime;

public class CachedExchangeRate {
    private final Duration intervalTime;
    private LocalDateTime expiredTime;
    private double exchangeRate;

    CachedExchangeRate(final Duration intervalTime) {
        this.intervalTime = intervalTime;
    }

    boolean isLatest(LocalDateTime now) {
        if (expiredTime == null) {
            return false;
        }

        return expiredTime.isAfter(now) || expiredTime.equals(now);
    }

    double getExchangeRate(LocalDateTime now) {
        if (!isLatest(now)) {
            throw new ShoppingException(ErrorCode.CACHED_INVALID);
        }

        return exchangeRate;
    }

    void updateExchangeRate(final double exchangeRate, final LocalDateTime now) {
        this.expiredTime = now.plus(intervalTime);
        this.exchangeRate = exchangeRate;
    }
}
