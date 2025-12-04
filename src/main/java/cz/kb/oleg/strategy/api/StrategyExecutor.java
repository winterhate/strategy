package cz.kb.oleg.strategy.api;

public interface StrategyExecutor<T, R> {

    Result<R> executeStrategy(Strategy<T, R> strategy, T t);

}
