package cz.kb.oleg.strategy.api;

public interface TestableStrategy<T> extends Strategy<T> {
    boolean canHandle(T input);
}
