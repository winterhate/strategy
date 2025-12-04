package cz.kb.oleg.strategy.service.selectors;

import cz.kb.oleg.strategy.api.ClassMappedStrategy;
import cz.kb.oleg.strategy.api.StrategySelector;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedStrategySelector<T, R, S extends ClassMappedStrategy<T, R>> implements StrategySelector<T, S> {

    private final Collection<S> testableStrategies;

    private final Map<Class<?>, List<S>> strategyMap = new HashMap<>();

    public CachedStrategySelector(Collection<S> testableStrategies) {
        this.testableStrategies = testableStrategies;
    }

    @Override
    public List<S> selectStrategies(T t) {
        if (t == null) {
            return List.of();
        }
        final Class<?> tClass = t.getClass();
        if (strategyMap.containsKey(tClass)) {
            return strategyMap.get(tClass);
        }
        final var applicableStrategies = testableStrategies.stream()
                .filter(strategy -> strategy.isApplicable(t))
                .toList();
        strategyMap.put(tClass, applicableStrategies);
        return applicableStrategies;
    }

}
