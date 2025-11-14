package cz.kb.oleg.strategy.service;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategySelector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapStrategySelector<T, S extends Strategy<T>> implements StrategySelector<T, S> {

    private final Map<Class<T>, S> strategyMap = new HashMap<>();

    @Override
    public List<S> selectStrategies(T t) {
        return List.of(selectStrategy(t));
    }

    public HashMapStrategySelector<T, S> withMapping(Class<T> clazz, S strategy) {
        strategyMap.put(clazz, strategy);
        return this;
    }

    private S selectStrategy(T t) {
        return strategyMap.get(t.getClass());
    }

}
