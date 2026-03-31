package com.winterhate.strategy.service.executors;

import com.winterhate.strategy.api.Strategy;
import com.winterhate.strategy.api.StrategyExecutor;
import com.winterhate.strategy.api.StrategyResultHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultStrategyExecutor<T> implements StrategyExecutor<T> {

    private final StrategyResultHandler<T> strategyResultHandler;

    public DefaultStrategyExecutor() {
        this(new DefaultStrategyResultHandler<>());
    }

    public DefaultStrategyExecutor(@NonNull StrategyResultHandler<T> strategyResultHandler) {
        this.strategyResultHandler = strategyResultHandler;
    }

    @Override
    public void executeStrategy(@NonNull Strategy<T> strategy, @NonNull T t) {
        try {
            strategy.apply(t);
            strategyResultHandler.onSuccess(strategy, t);
        } catch (Exception e) {
            strategyResultHandler.onException(strategy, t, e);
        }
    }

}
