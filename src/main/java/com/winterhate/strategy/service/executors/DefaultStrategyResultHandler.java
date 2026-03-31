package com.winterhate.strategy.service.executors;

import com.winterhate.strategy.api.Strategy;
import com.winterhate.strategy.api.StrategyResultHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultStrategyResultHandler<T> implements StrategyResultHandler<T> {

    @Override
    public void onSuccess(@NonNull Strategy<T> strategy, @NonNull T data) {
        log.debug("Strategy {} has been successfully executed", strategy);
    }

    @Override
    public void onException(@NonNull Strategy<T> strategy, @NonNull T data, @NonNull Exception e) {
        final var strategyName = getStrategyName(strategy);
        log.error("Strategy {} execution failed with error: {}", strategyName, e.getMessage(), e);
    }

    protected String getStrategyName(Strategy<T> strategy) {
        return strategy.getClass().getSimpleName();
    }

}
