package shopping.infrastructure;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class CacheProvider {

    private static final long CACHE_EXPIRES_SECOND = 60 * 60;

    private final Map<Class<?>, CacheInfo<?>> cache = new HashMap<>();
    private final Clock clock;


    public CacheProvider(final Clock clock) {
        this.clock = clock;
    }

    public<T> void put(Class<T> clazz, Supplier<T> cacheSupplier) {
        CacheInfo<T> cacheInfo =  new CacheInfo<>(cacheSupplier, getCurrentTime());
        cache.put(clazz, cacheInfo);
    }

    public <T> T get(Class<T> clazz) {
        if (cache.containsKey(clazz)) {
            return (T) cache.get(clazz).getCached(getCurrentTime());
        }
        return null;
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now(clock);
    }

    static class CacheInfo<T> {

        private T cached;
        private LocalDateTime lastModifiedAt;
        private final Supplier<T> cacheSupplier;

        public CacheInfo(final Supplier<T> cacheSupplier, final LocalDateTime initializedAt) {
            this.cached = cacheSupplier.get();
            this.cacheSupplier = cacheSupplier;
            this.lastModifiedAt = initializedAt;
        }

        public T getCached(LocalDateTime now) {
            if (isCacheNotInitialized() || isCacheExpired(now)) {
                updateCached(now);
            }
            return cached;
        }

        private boolean isCacheExpired(LocalDateTime now) {
            return lastModifiedAt.plusSeconds(CACHE_EXPIRES_SECOND).isBefore(now);
        }

        private boolean isCacheNotInitialized() {
            return Objects.isNull(cached);
        }

        private void updateCached(LocalDateTime now) {
            cached = cacheSupplier.get();
            lastModifiedAt = now;
        }
    }
}
