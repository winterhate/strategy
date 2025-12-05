package cz.kb.oleg.strategy.service.helpers;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class AtomicFieldCache<T> {
    // Holds the cached value
    private final AtomicReference<T> cache = new AtomicReference<>();

    /**
     * Returns the cached value if present, otherwise computes it using the supplier.
     * Thread-safe and lock-free.
     */
    public T getOrLoad(Supplier<T> supplier) {
        T value = cache.get();
        if (value == null) {
            // Compute new value
            T newValue = supplier.get();
            // Atomically set if still null
            if (cache.compareAndSet(null, newValue)) {
                return newValue;
            } else {
                // Another thread already set it
                return cache.get();
            }
        }
        return value;
    }

    /**
     * Forcefully updates the cache with a new value.
     */
    public void update(T newValue) {
        cache.set(newValue);
    }

    /**
     * Clears the cache.
     */
    public void clear() {
        cache.set(null);
    }

    /**
     * Returns the current cached value (may be null).
     */
    public T peek() {
        return cache.get();
    }

}
