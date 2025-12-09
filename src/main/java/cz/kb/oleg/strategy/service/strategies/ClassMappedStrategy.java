package cz.kb.oleg.strategy.service.strategies;

import cz.kb.oleg.strategy.api.Strategy;
import lombok.NonNull;

public interface ClassMappedStrategy<T> extends Strategy<T> {

    @NonNull
    Class<? extends T> getApplicableClass();

}
