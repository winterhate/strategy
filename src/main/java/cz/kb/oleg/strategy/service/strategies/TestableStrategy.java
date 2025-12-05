package cz.kb.oleg.strategy.service.strategies;

import cz.kb.oleg.strategy.api.Strategy;

public interface TestableStrategy<T, R> extends Strategy<T, R> {

    boolean isApplicable(T input);

}
