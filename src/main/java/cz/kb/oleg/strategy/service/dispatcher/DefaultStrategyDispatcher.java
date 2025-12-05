package cz.kb.oleg.strategy.service.dispatcher;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyDispatcher;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import cz.kb.oleg.strategy.api.StrategySelector;
import cz.kb.oleg.strategy.service.executors.DefaultStrategyExecutor;
import cz.kb.oleg.strategy.service.selectors.DefaultStrategySelector;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public class DefaultStrategyDispatcher<T, R, S extends Strategy<T, R>> implements StrategyDispatcher<T, R> {

    private final StrategySelector<T, S> strategySelector;
    private final StrategyExecutor<T, R> strategyExecutor;

    public DefaultStrategyDispatcher(Collection<S> strategies) {
        this(new DefaultStrategySelector<>(strategies), new DefaultStrategyExecutor<>());
    }

    public DefaultStrategyDispatcher(StrategySelector<T, S> strategySelector) {
        this(strategySelector, new DefaultStrategyExecutor<>());
    }

    public DefaultStrategyDispatcher(StrategySelector<T, S> strategySelector, StrategyExecutor<T, R> strategyExecutor) {
        this.strategyExecutor = strategyExecutor;
        this.strategySelector = strategySelector;
    }

    @Override
    public List<Result<R>> dispatch(T data) {
        return strategySelector.selectStrategies(data).stream()
                .map(strategy -> strategyExecutor.executeStrategy(strategy, data))
                .toList();
    }

}
