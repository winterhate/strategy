package cz.kb.oleg.strategy.service.strategies;

import cz.kb.oleg.strategy.api.Strategy;
import lombok.NonNull;

public interface TestableStrategy<T, R> extends Strategy<T, R> {

    boolean isApplicable(@NonNull T input);

}
