package cz.kb.oleg.strategy.service.executors;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingStrategyExecutor<T> extends DefaultStrategyExecutor<T> {

    private final StrategyExecutor<T> strategyExecutorDelegate;

    public LoggingStrategyExecutor(StrategyExecutor<T> strategyExecutorDelegate) {
        this.strategyExecutorDelegate = strategyExecutorDelegate;
    }

    @Override
    public void executeStrategy(Strategy<T> strategy, T t) {
        log.info("Executing strategy {} with {}", strategy.getClass().getSimpleName(), t.toString());
        strategyExecutorDelegate.executeStrategy(strategy, t);
    }

}
