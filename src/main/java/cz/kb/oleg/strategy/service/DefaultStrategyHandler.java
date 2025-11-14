package cz.kb.oleg.strategy.service;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import cz.kb.oleg.strategy.api.StrategyHandler;
import cz.kb.oleg.strategy.api.StrategySelector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultStrategyHandler<T, S extends Strategy<T>> implements StrategyHandler<T> {

    private final StrategySelector<T, S> strategySelector;
    private final StrategyExecutor<T> strategyExecutor;

    public DefaultStrategyHandler(StrategySelector<T, S> strategySelector) {
        this(new DefaultStrategyExecutor<>(), strategySelector);
    }

    public DefaultStrategyHandler(StrategyExecutor<T> strategyExecutor, StrategySelector<T, S> strategySelector) {
        this.strategyExecutor = strategyExecutor;
        this.strategySelector = strategySelector;
    }

    @Override
    public void handle(T data) {
        strategySelector.selectStrategies(data).forEach(strategy -> {
            try {
                strategyExecutor.executeStrategy(strategy, data);
            } catch (Exception e) {
                onException(e, strategy, data);
            }
        });
    }

    protected void onException(Exception e, Strategy<T> strategy, T data) {
        log.error("{}: Exception occurred while handling data: {}", strategy.getClass().getSimpleName(), e.getMessage(), e);
    }

}
