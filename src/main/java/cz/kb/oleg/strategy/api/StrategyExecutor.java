package cz.kb.oleg.strategy.api;

public interface StrategyExecutor<T> {

    void executeStrategy(Strategy<T> strategy, T t);

    void handleStrategyException(Strategy<T> strategy, T data, Exception e);

}
