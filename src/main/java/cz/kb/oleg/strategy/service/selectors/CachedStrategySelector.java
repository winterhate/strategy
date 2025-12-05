package cz.kb.oleg.strategy.service.selectors;

import cz.kb.oleg.strategy.api.StrategySelector;
import cz.kb.oleg.strategy.service.strategies.ClassMappedStrategy;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedStrategySelector<T, R, S extends ClassMappedStrategy<T, R>> implements StrategySelector<T, S> {

    private final Map<Class<?>, List<S>> strategyMap = new HashMap<>();

    public CachedStrategySelector(Collection<S> classMappedStrategies) {
        classMappedStrategies.stream()
                .map(ClassMappedStrategy::getApplicableClass)
                .forEach(cls -> strategyMap.put(cls, classMappedStrategies.stream()
                        .filter(strategy -> strategy.getApplicableClass().equals(cls))
                        .toList()));
    }

    @Override
    public List<S> selectStrategies(T t) {
        if (t == null) {
            return List.of();
        }
        return strategyMap.getOrDefault(t.getClass(), List.of());
    }

}
