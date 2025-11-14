package cz.kb.oleg.strategy.api;

public interface StrategyHandler<T> {

    void handle(T data);

}
