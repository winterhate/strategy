package cz.kb.oleg.strategy.api;

public interface TestableStrategy<T, R> extends Strategy<T, R> {

    boolean isApplicable(T input);

}
