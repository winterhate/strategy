package cz.kb.oleg.strategy.service.selectors;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategySelector;
import lombok.NonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class DefaultStrategySelector<T, S extends Strategy<T>> implements StrategySelector<T, S> {

    private final List<S> testableStrategies;

    public DefaultStrategySelector(Collection<S> testableStrategies) {
        this.testableStrategies = testableStrategies.stream()
                .sorted(Comparator.comparing(s -> s.getClass().getSimpleName()))
                .toList();
    }

    @Override
    public List<S> selectStrategies(@NonNull T t) {
        return testableStrategies;
    }

}
