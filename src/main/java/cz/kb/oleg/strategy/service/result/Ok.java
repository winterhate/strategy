package cz.kb.oleg.strategy.service.result;

import cz.kb.oleg.strategy.api.Result;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A Result type that holds a value of type T.
 *
 * @param <T> the type of the value
 *
 */
public final class Ok<T> implements Result<T> {

    private final T value;

    public Ok(T value) {
        this.value = Objects.requireNonNull(value);
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
        return false;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T getOr(T other) {
        return value;
    }

    @Override
    public T getOr(Function<Throwable, T> function) {
        return value;
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
        okConsumer.accept(value);
        return this;
    }

    @Override
    public Result<T> on(Consumer<T> okConsumer, Consumer<Throwable> errConsumer) {
        okConsumer.accept(value);
        return this;
    }

    @Override
    public Result<T> onErr(Consumer<Throwable> errConsumer) {
        return this;
    }

    @Override
    public <R> Result<R> map(Function<T, Result<R>> okMapper) {
        return Objects.requireNonNull(okMapper.apply(value));
    }

    @Override
    public <R> R mapOr(Function<T, R> okMapper, Function<Throwable, R> errMapper) {
        return Objects.requireNonNull(okMapper.apply(value));
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
        if (obj instanceof Ok<?> other) {
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
        return "Ok[" + value + "]";
    }
}
