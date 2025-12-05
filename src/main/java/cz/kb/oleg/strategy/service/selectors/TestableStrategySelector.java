package cz.kb.oleg.strategy.service.selectors;

import cz.kb.oleg.strategy.api.StrategySelector;
import cz.kb.oleg.strategy.service.strategies.TestableStrategy;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class TestableStrategySelector<T, R, S extends TestableStrategy<T, R>> implements StrategySelector<T, S> {

    private final List<S> testableStrategies;

    public TestableStrategySelector(Collection<S> testableStrategies) {
        this.testableStrategies = testableStrategies.stream()
                .sorted(Comparator.comparing(s -> s.getClass().getSimpleName()))
                .toList();
    }

    @Override
    public List<S> selectStrategies(T t) {
        if (t == null) {
            return List.of();
        }
        return testableStrategies.stream()
                .filter(strategy -> strategy.isApplicable(t))
                .toList();
    }

}
