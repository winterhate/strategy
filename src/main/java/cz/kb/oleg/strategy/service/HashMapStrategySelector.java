package cz.kb.oleg.strategy.service;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategySelector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HashMapStrategySelector<T, S extends Strategy<T>> implements StrategySelector<T, S> {

    private final Map<Class<T>, S> strategyMap = new HashMap<>();

    @Override
    public List<S> selectStrategies(T t) {
        return selectStrategy(t)
                .map(List::of)
                .orElse(List.of());
    }

    public HashMapStrategySelector<T, S> withMapping(Class<T> clazz, S strategy) {
        strategyMap.put(clazz, strategy);
        return this;
    }

    private Optional<S> selectStrategy(T t) {
        return Optional.ofNullable(strategyMap.get(t.getClass()));
    }

}
