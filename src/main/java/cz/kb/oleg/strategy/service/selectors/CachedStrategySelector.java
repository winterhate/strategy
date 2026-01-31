package cz.kb.oleg.strategy.service.selectors;

import cz.kb.oleg.strategy.api.StrategySelector;
import cz.kb.oleg.strategy.service.strategies.ClassMappedStrategy;
import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedStrategySelector<T, S extends ClassMappedStrategy<T>> implements StrategySelector<T, S> {

    private final Map<Class<?>, List<S>> strategyMap = new HashMap<>();

    public CachedStrategySelector(Collection<S> classMappedStrategies) {
        classMappedStrategies.stream()
                .map(ClassMappedStrategy::getApplicableClass)
                .forEach(cls -> strategyMap.put(cls, classMappedStrategies.stream()
                        .filter(strategy -> cls.isAssignableFrom(strategy.getApplicableClass()))
                        .toList()));
    }

    @Override
    public List<S> selectStrategies(@NonNull T t) {
        return strategyMap.getOrDefault(t.getClass(), List.of());
    }

}
