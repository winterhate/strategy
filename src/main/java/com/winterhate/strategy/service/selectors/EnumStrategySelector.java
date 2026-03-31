package com.winterhate.strategy.service.selectors;

import com.winterhate.strategy.api.StrategySelector;
import com.winterhate.strategy.service.strategies.EnumMappedStrategy;
import lombok.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EnumStrategySelector<
        T extends EnumStrategyData<E>,
        E extends Enum<E>,
        S extends EnumMappedStrategy<T, E>
    > implements StrategySelector<T, S> {

    private final MultiValueMap<E, S> enumMappedStrategies = new LinkedMultiValueMap<>();

    public EnumStrategySelector(Collection<S> enumMappedStrategies) {
        enumMappedStrategies.forEach(strategy -> this.enumMappedStrategies.add(strategy.handles(), strategy));
    }

    @Override
    public List<S> selectStrategies(@NonNull T t) {
        List<S> strategies = enumMappedStrategies.get(t.getSelector());
        return strategies != null ? strategies : Collections.emptyList();
    }

}
