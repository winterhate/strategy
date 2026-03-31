package com.winterhate.strategy.service.strategies;

import com.winterhate.strategy.api.Strategy;
import lombok.NonNull;

public interface TestableStrategy<T> extends Strategy<T> {

    boolean isApplicable(@NonNull T input);

}
