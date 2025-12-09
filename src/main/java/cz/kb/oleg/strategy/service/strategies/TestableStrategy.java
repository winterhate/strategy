package cz.kb.oleg.strategy.service.strategies;

import cz.kb.oleg.strategy.api.Strategy;
import lombok.NonNull;

public interface TestableStrategy<T> extends Strategy<T> {

    boolean isApplicable(@NonNull T input);

}
