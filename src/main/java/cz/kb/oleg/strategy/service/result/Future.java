package cz.kb.oleg.strategy.service.result;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.service.helpers.AtomicFieldCache;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Future<T> implements Result<T> {

    private final java.util.concurrent.Future<Result<T>> value;
    private final AtomicFieldCache<Result<T>> resolvedCache = new AtomicFieldCache<>();

    public Future(java.util.concurrent.Future<Result<T>> value) {
        this.value = value;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isErr() {
        return false;
    }

    @Override
    public boolean isFuture() {
        return true;
    }

    @Override
    public Result<T> resolve() {
        return resolvedCache.getOrLoad(this::fullyResolveResult);
    }

    private Result<T> fullyResolveResult() {
        Result<T> result = this;
        while (result.isFuture()) {
            try {
                result = value.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                result = Result.Err(e);
                break;
            } catch (ExecutionException e) {
                result = Result.Err(e);
                break;
            }
        }
        return result;
    }

    @Override
    public T get() {
        return resolve().get();
    }

    @Override
    public T getOr(T other) {
        return get();
    }

    @Override
    public T getOr(Function<Throwable, T> function) {
        return get();
    }

    @Override
    public Throwable error() {
        throw new NoSuchElementException("No error present");
    }

    @Override
    public String getErrorMessage() {
        throw new NoSuchElementException("No error present");
    }

    @Override
    public void printErrorMessage() {
        throw new NoSuchElementException("No error present");
    }

    @Override
    public Result<T> onOk(Consumer<T> okConsumer) {
        okConsumer.accept(get());
        return this;
    }

    @Override
    public Result<T> on(Consumer<T> okConsumer, Consumer<Throwable> errConsumer) {
        okConsumer.accept(get());
        return this;
    }

    @Override
    public Result<T> onErr(Consumer<Throwable> errConsumer) {
        return this;
    }

    @Override
    public <R> Result<R> map(Function<T, Result<R>> okMapper) {
        return Objects.requireNonNull(okMapper.apply(get()));
    }

    @Override
    public <R> R mapOr(Function<T, R> okMapper, Function<Throwable, R> errMapper) {
        return Objects.requireNonNull(okMapper.apply(get()));
    }

    /**
     * Indicates whether some other object is "equal to" this {@code Ok}.
     * The other object is considered equal if:
     * <ul>
     * <li>it is also an {@code Ok} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code equals()}.
     * </ul>
     *
     * @param obj an object to be tested for equality
     * @return {@code true} if the other object is "equal to" this object
     * otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Future<?> other) {
            return Objects.equals(this.value, other.value);
        }
        return false;
    }

    /**
     * Returns the hash code of the value, if present, otherwise {@code 0}
     * (zero) if no value is present.
     *
     * @return hash code value of the present value or {@code 0} if no value is
     * present
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * Returns a string representation of this {@code Ok}
     * suitable for debugging.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return "Future[" + value + "]";
    }
}
