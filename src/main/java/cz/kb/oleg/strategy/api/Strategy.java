package cz.kb.oleg.strategy.api;

public interface Strategy<T, R> {

    Result<R> apply(T t);

}
