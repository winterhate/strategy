package cz.kb.oleg.strategy.service.selectors;

import cz.kb.oleg.strategy.api.StrategySelector;
import cz.kb.oleg.strategy.api.TestableStrategy;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class TestableStrategySelector<T, R, S extends TestableStrategy<T, R>> implements StrategySelector<T, S> {

    private final Collection<S> testableStrategies;

    public TestableStrategySelector(Collection<S> testableStrategies) {
        this.testableStrategies = testableStrategies;
    }

    @Override
    public List<S> selectStrategies(T t) {
        return testableStrategies.stream()
                .filter(strategy -> strategy.isApplicable(t))
                .sorted(Comparator.comparing(s -> s.getClass().getSimpleName()))
                .toList();
    }

}
