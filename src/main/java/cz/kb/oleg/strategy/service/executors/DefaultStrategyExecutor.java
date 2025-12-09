package cz.kb.oleg.strategy.service.executors;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExceptionHandler;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultStrategyExecutor<T> implements StrategyExecutor<T> {

    private final StrategyExceptionHandler<T> strategyExceptionHandler;

    public DefaultStrategyExecutor() {
        this(new DefaultStrategyExceptionHandler<>());
    }

    public DefaultStrategyExecutor(StrategyExceptionHandler<T> strategyExceptionHandler) {
        this.strategyExceptionHandler = strategyExceptionHandler;
    }

    @Override
    public void executeStrategy(Strategy<T> strategy, T t) {
        try {
            strategy.apply(t);
        } catch (Exception e) {
            handleStrategyException(strategy, t, e);
        }
    }

    @Override
    public void handleStrategyException(Strategy<T> strategy, T data, Exception e) {
        strategyExceptionHandler.handleException(strategy, data, e);
    }

}
