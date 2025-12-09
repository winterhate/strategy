package cz.kb.oleg.strategy.api;

public interface StrategyExceptionHandler<T> {
    void handleException(Strategy<T> strategy, T data, Exception e);
}
