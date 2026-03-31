package com.winterhate.strategy.service.executors;

import com.winterhate.strategy.api.Strategy;
import com.winterhate.strategy.api.StrategyExecutor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingStrategyExecutor<T> implements StrategyExecutor<T> {

    private final StrategyExecutor<T> strategyExecutorDelegate;

    public LoggingStrategyExecutor(@NonNull StrategyExecutor<T> strategyExecutorDelegate) {
        this.strategyExecutorDelegate = strategyExecutorDelegate;
    }

    @Override
    public void executeStrategy(@NonNull Strategy<T> strategy, @NonNull T t) {
        log.info("Executing strategy {} with {}", strategy.getClass().getSimpleName(), t);
        strategyExecutorDelegate.executeStrategy(strategy, t);
    }

}
