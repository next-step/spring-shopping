package shopping.infrastructure;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

public class CacheProvider<T> {

    private static final int CACHE_EXPIRES_SECOND = 60 * 60;

    private T cacheTarget;
    private LocalDateTime lastModifiedAt;
    private final Clock clock;
    private final Supplier<T> cacheSupplier;

    public CacheProvider(final Supplier<T> cacheSupplier, final Clock clock) {
        this.clock = clock;
        this.cacheSupplier = cacheSupplier;
        lastModifiedAt = getCurrentTime();
    }

    public T getData() {
        if (isCacheNotInitialized() || isCacheExpired()) {
            updateCache();
        }
        return cacheTarget;
    }

    public void updateCache() {
        this.cacheTarget = cacheSupplier.get();
        this.lastModifiedAt = getCurrentTime();
    }

    private boolean isCacheNotInitialized() {
        return Objects.isNull(cacheTarget);
    }

    private boolean isCacheExpired() {
        return lastModifiedAt.plusSeconds(CACHE_EXPIRES_SECOND).isBefore(getCurrentTime());
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now(clock);
    }
}
