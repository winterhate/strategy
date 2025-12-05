package cz.kb.oleg.strategy.service.strategies;

import cz.kb.oleg.strategy.api.Strategy;
import lombok.NonNull;

public interface ClassMappedStrategy<T, R> extends Strategy<T, R> {

    @NonNull
    Class<? extends T> getApplicableClass();

}
