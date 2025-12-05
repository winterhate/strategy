package cz.kb.oleg.strategy.service.result;

import cz.kb.oleg.strategy.api.Result;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A Result type that holds an error message.
 *
 * @param <T> the type of the value
 *
 */
public final class Err<T> implements Result<T> {

    private final Throwable error;

    /**
     * Constructs an {@link Err} with the specified error.
     *
     * @param error the error to be present in the {@link Err}
     */
    public Err(Throwable error) {
        this.error = Objects.requireNonNull(error);
    }

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public boolean isErr() {
        return true;
    }

    @Override
    public boolean isFuture() {
        return false;
    }

    @Override
    public T get() {
        throw new NoSuchElementException("No value present: " + error.getMessage());
    }

    @Override
    public T getOr(T other) {
        return Objects.requireNonNull(other);
    }

    @Override
    public T getOr(Function<Throwable, T> errMapper) {
        return Objects.requireNonNull(errMapper.apply(error));
    }

    @Override
    public Throwable error() {
        return error;
    }

    @Override
    public String getErrorMessage() {
        return error.getMessage();
    }

    @Override
    public void printErrorMessage() {
        System.out.println(error.getMessage());
    }

    @Override
    public Result<T> onOk(Consumer<T> okConsumer) {
        return this;
    }

    @Override
    public Result<T> on(Consumer<T> okConsumer, Consumer<Throwable> errConsumer) {
        errConsumer.accept(error);
        return this;
    }

    @Override
    public Result<T> onErr(Consumer<Throwable> errConsumer) {
        errConsumer.accept(error);
        return this;
    }

    @Override
    public <R> Result<R> map(Function<T, Result<R>> okMapper) {
        return new Err<>(error);
    }

    @Override
    public <R> R mapOr(Function<T, R> okMapper, Function<Throwable, R> errMapper) {
        return Objects.requireNonNull(errMapper.apply(error));
    }

    /**
     * Indicates whether some other object is "equal to" this {@code Err}.
     * The other object is considered equal if:
     * <ul>
     * <li>it is also an {@code Err} and;
     * <li>both instances have no error present or;
     * <li>the present errors are "equal to" each other via {@code equals()}.
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
        if (obj instanceof Err<?> other) {
            return Objects.equals(this.error, other.error);
        }
        return false;
    }

    /**
     * Returns the hash code of the error, if present, otherwise {@code 0}
     * (zero) if no error is present.
     *
     * @return hash code value of the present error or {@code 0} if no error is
     * present
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(error);
    }

    /**
     * Returns a string representation of this {@code Err}
     * suitable for debugging.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return "Err[" + error + "]";
    }
}
