package cz.kb.oleg.strategy.api;

public interface TestableStrategy<T> extends Strategy<T, Result.Void> {

    boolean isApplicable(T input);

}
