package cz.kb.oleg.strategy.service.executors;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import cz.kb.oleg.strategy.api.StrategyResultHandler;
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
