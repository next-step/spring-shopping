package shopping.infrastructure;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

public class CacheProvider<T> {

    private static final int CACHE_EXPIRES_SECOND = 60 * 60;

    private T cacheTarget;
    private LocalDateTime lastModifiedAt;
    private final Supplier<T> cacheSupplier;

    public CacheProvider(final Supplier<T> cacheSupplier) {
        lastModifiedAt = LocalDateTime.now();
        this.cacheSupplier = cacheSupplier;
    }

    public T getData() {
        if (isCacheNotInitialized() || isCacheExpired(LocalDateTime.now())) {
            updateCache();
        }
        return cacheTarget;
    }

    public void updateCache() {
        this.cacheTarget = cacheSupplier.get();
        this.lastModifiedAt = LocalDateTime.now();
    }

    private boolean isCacheNotInitialized() {
        return Objects.isNull(cacheTarget);
    }

    private boolean isCacheExpired(final LocalDateTime compare) {
        return lastModifiedAt.plusSeconds(CACHE_EXPIRES_SECOND).isBefore(compare);
    }
}
