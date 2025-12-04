package cz.kb.oleg.strategy.api;

public interface StrategyDispatcher<T> {

    void dispatch(T data);

}
