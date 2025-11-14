package cz.kb.oleg.strategy.decisions;

import cz.kb.oleg.strategy.api.StrategyExecutor;
import cz.kb.oleg.strategy.api.TestableStrategy;
import cz.kb.oleg.strategy.service.DefaultStrategyHandler;
import cz.kb.oleg.strategy.service.TestableStrategySelector;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TestableStrategyHandler<T, S extends TestableStrategy<T>> extends DefaultStrategyHandler<T, S> {

    public TestableStrategyHandler(List<S> strategies) {
        super(new TestableStrategySelector<>(strategies));
    }

    public TestableStrategyHandler(StrategyExecutor<T> strategyExecutor, List<S> strategies) {
        super(strategyExecutor, new TestableStrategySelector<>(strategies));
    }

}
