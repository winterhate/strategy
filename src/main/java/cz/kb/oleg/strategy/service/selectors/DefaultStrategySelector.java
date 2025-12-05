package cz.kb.oleg.strategy.service.selectors;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategySelector;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class DefaultStrategySelector<T, R, S extends Strategy<T, R>> implements StrategySelector<T, S> {

    private final List<S> testableStrategies;

    public DefaultStrategySelector(Collection<S> testableStrategies) {
        this.testableStrategies = testableStrategies.stream()
                .sorted(Comparator.comparing(s -> s.getClass().getSimpleName()))
                .toList();
    }

    @Override
    public List<S> selectStrategies(T t) {
        return testableStrategies;
    }

}
