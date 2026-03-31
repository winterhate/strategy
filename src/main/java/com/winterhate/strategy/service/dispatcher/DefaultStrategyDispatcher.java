package com.winterhate.strategy.service.dispatcher;

import com.winterhate.strategy.api.Strategy;
import com.winterhate.strategy.api.StrategyDispatcher;
import com.winterhate.strategy.api.StrategyExecutor;
import com.winterhate.strategy.api.StrategySelector;
import com.winterhate.strategy.service.executors.DefaultStrategyExecutor;
import com.winterhate.strategy.service.selectors.DefaultStrategySelector;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class DefaultStrategyDispatcher<T, S extends Strategy<T>> implements StrategyDispatcher<T> {

    private final StrategySelector<T, S> strategySelector;
    private final StrategyExecutor<T> strategyExecutor;

    public DefaultStrategyDispatcher(Collection<S> strategies) {
        this(new DefaultStrategySelector<>(strategies), new DefaultStrategyExecutor<>());
    }

    public DefaultStrategyDispatcher(StrategySelector<T, S> strategySelector) {
        this(strategySelector, new DefaultStrategyExecutor<>());
    }

    public DefaultStrategyDispatcher(StrategySelector<T, S> strategySelector, StrategyExecutor<T> strategyExecutor) {
        this.strategyExecutor = strategyExecutor;
        this.strategySelector = strategySelector;
    }

    @Override
    public void dispatch(@NonNull T data) {
        strategySelector.selectStrategies(data)
                .forEach(strategy -> strategyExecutor.executeStrategy(strategy, data));
    }

}
