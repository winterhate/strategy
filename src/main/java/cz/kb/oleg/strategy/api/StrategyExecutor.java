package cz.kb.oleg.strategy.api;

public interface StrategyExecutor<T, R> {

    void executeStrategy(Strategy<T, R> strategy, T t);

}
