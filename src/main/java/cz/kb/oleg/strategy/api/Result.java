package cz.kb.oleg.strategy.api;

import cz.kb.oleg.strategy.service.result.Err;
import cz.kb.oleg.strategy.service.result.Future;
import cz.kb.oleg.strategy.service.result.NoValue;
import cz.kb.oleg.strategy.service.result.Ok;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A result type is a container object much like an {@link Optional}, but it
 * can also contain an error. If a value is present, {@link #isOk()} returns
 * {@code true} and the result is an {@link Ok} containing the value. The
 * contained value can be accessed with {@link #get()}. If an error is
 * present, {@link #isOk()} returns {@code false} and the result is an
 * {@link Err} containing the error. The error message can be accessed with
 * {@link #error()}, {@link #getErrorMessage()} or {@link #printErrorMessage()}.
 * The contained value or error are guaranteed to be non-{@code null}.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #getOr(Object)} or {@link #getOr(Function)}.
 *
 * <p>{@code Result} is primarily intended for use as a method return type for
 * methods that can fail and would traditionally throw an error. This reduces
 * the need for {@code try/catch} blocks which leads to cleaner, more readable
 * and maintainable code with better control flow.
 *
 * @param <T> the type of value
 * @since 1.2
 */
@SuppressWarnings("unused")
public sealed interface Result<T> permits Err, Future, Ok {
    /**
     * Returns true if the result is {@link Ok}, otherwise false
     *
     * @return true if the result is {@link Ok}, otherwise false.
     */
    boolean isOk();

    /**
     * Returns true if the result is {@link Err}, otherwise false
     *
     * @return true if the result is {@link Err}, otherwise false.
     */
    boolean isErr();

    /**
     * Returns true if the result is {@link Future}, otherwise false
     *
     * @return true if the result is {@link Err}, otherwise false.
     */
    boolean isFuture();

    default Result<T> resolve() {
        return this;
    }

    /**
     * Returns the contained {@link Ok} value.
     * Throws if the value is an {@link Err}, with an error message provided by the
     * {@link Err}.
     * Only call this method if {@link #isOk()} returns true.
     *
     * @return the contained {@link Ok} value
     * @throws NoSuchElementException if the result is an {@link Err}
     */
    T get();

    /**
     * If result is {@link Ok}, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if the result is {@link Err}.
     * @return the value if the result is {@link Ok}, otherwise {@code other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    T getOr(T other);

    /**
     * If the result is an {@link Ok}, returns the value, otherwise returns the result
     * produced by the supplying function.
     *
     * @param function the supplying function that produces a value to be returned
     * @return the value, if present, otherwise the result produced by the
     * supplying function
     * @throws NullPointerException if the supplying function or the result produced
     *                              by the supplying function is {@code null}
     */
    T getOr(Function<Throwable, T> function);

    /**
     * Returns the error message if the result is an {@link Err}.
     *
     * @return the error message if the result is an {@link Err}
     * @throws NoSuchElementException if the result is an {@link Ok}
     */
    Throwable error();

    /**
     * Returns the error message if the result is an {@link Err}, otherwise throws
     * an exception.
     *
     * @return the error message if the result is an {@link Err}
     * @throws NoSuchElementException if the result is an {@link Ok}
     */
    String getErrorMessage();

    /**
     * Prints the error message if the result is an {@link Err}.
     *
     * @throws NoSuchElementException if the result is an {@link Ok}
     */
    void printErrorMessage();

    /**
     * If the result is an {@link Ok}, performs the given action with the value,
     * otherwise performs the given action with the error.
     *
     * @param okConsumer  the action to be performed, if the result is an {@link Ok}
     * @param errConsumer the action to be performed, if the result is an {@link Err}
     * @return the result itself
     */
    Result<T> on(Consumer<T> okConsumer, Consumer<Throwable> errConsumer);

    /**
     * If the result is an {@link Ok}, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param okConsumer the action to be performed, if the result is an {@link Ok}
     * @return the result itself
     */
    Result<T> onOk(Consumer<T> okConsumer);

    /**
     * If the result is an {@link Err}, performs the given action with the error,
     * otherwise does nothing.
     *
     * @param errConsumer the action to be performed, if the result is an {@link Err}
     * @return the result itself
     */
    Result<T> onErr(Consumer<Throwable> errConsumer);

    /**
     * If the result is an {@link Ok}, performs the given action with the value,
     * returning a new Result, otherwise does nothing.
     *
     * @param <R>      the type of the value of the new {@link Result}
     * @param okMapper the action to be performed, if the result is an {@link Ok}
     * @return the mapped result
     */
    <R> Result<R> map(Function<T, Result<R>> okMapper);

    /**
     * If the result is an {@link Ok}, performs the given action with the value,
     * otherwise performs the given action with the error.
     *
     * @param <R>       the type of the value of the new {@link Result}
     * @param okMapper  the action to be performed, if the result is an {@link Ok}
     * @param errMapper the action to be performed, if the result is an {@link Err}
     * @return the mapped result
     */
    <R> R mapOr(Function<T, R> okMapper, Function<Throwable, R> errMapper);

    /**
     * Returns an {@link Ok} with the specified value.
     *
     * @param <T>   the type of the value
     * @param value the value to be present in the {@link Ok}
     * @return an {@link Ok} with the specified value, {@link Err} if the value is null
     */
    static <T> Result<T> Ok(T value) {
        return new Ok<>(value);
    }

    /**
     * Returns an {@link Ok} with a value of {@code true}.
     *
     * @return an {@link Ok} with a value of {@code true}
     */
    static Result<NoValue> Ok() {
        return new Ok<>(NoValue.getInstance());
    }

    /**
     * Returns an {@link Err} with the specified error.
     *
     * @param <T>   the type of the value
     * @param error the error to be present in the {@link Err}
     * @return an {@link Err} with the specified error or {@link Err} holding a NullPointerException if the error is null
     */
    static <T> Result<T> Err(Throwable error) {
        return new Err<>(error);
    }

    /**
     * Returns an {@link Ok} with the specified value.
     *
     * @param value the value to be present in the {@link Ok}
     * @return an {@link Ok} with the specified value, {@link Err} if the value is null
     */
    static <T> Result<T> Future(java.util.concurrent.Future<Result<T>> value) {
        return new Future<>(value);
    }

}
