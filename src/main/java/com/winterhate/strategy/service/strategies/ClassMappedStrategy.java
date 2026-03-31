package com.winterhate.strategy.service.strategies;

import com.winterhate.strategy.api.Strategy;
import lombok.NonNull;

public interface ClassMappedStrategy<T> extends Strategy<T> {

    @NonNull
    Class<? extends T> getApplicableClass();

}
