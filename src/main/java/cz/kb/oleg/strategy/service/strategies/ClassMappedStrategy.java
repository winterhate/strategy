package cz.kb.oleg.strategy.service.strategies;

import cz.kb.oleg.strategy.api.Strategy;

public interface ClassMappedStrategy<T, R> extends Strategy<T, R> {

    Class<? extends T> getApplicableClass();

}
