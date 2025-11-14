package cz.kb.oleg.strategy.service;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;

public class DefaultStrategyExecutor<T> implements StrategyExecutor<T> {

    @Override
    public void executeStrategy(Strategy<T> strategy, T data) {
        strategy.handle(data);
    }

}
