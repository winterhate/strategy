package cz.kb.oleg.strategy.service.executors;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultStrategyExceptionHandler<T> implements StrategyExceptionHandler<T> {

    @Override
    public void handleException(Strategy<T> strategy, T data, Exception e) {
        final var strategyName = getStrategyName(strategy);
        log.error("Strategy {} execution failed with error: {}", strategyName, e.getMessage(), e);
    }

    protected String getStrategyName(Strategy<T> strategy) {
        return strategy.getClass().getSimpleName();
    }

}
