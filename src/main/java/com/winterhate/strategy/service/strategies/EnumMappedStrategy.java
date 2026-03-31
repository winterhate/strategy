package com.winterhate.strategy.service.strategies;

import com.winterhate.strategy.api.Strategy;
import lombok.NonNull;

public interface EnumMappedStrategy<T, E extends Enum<E>> extends Strategy<T> {

    @NonNull
    E handles();

}
