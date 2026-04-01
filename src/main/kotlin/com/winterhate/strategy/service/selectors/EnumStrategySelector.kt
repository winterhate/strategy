package com.winterhate.strategy.service.selectors

import com.winterhate.strategy.api.StrategySelector
import com.winterhate.strategy.service.strategies.EnumMappedStrategy
import org.springframework.util.LinkedMultiValueMap

class EnumStrategySelector<
    T : EnumStrategyData<E>,
    E : Enum<E>,
    S : EnumMappedStrategy<T, E>
>(
    enumMappedStrategies: Collection<S>
) : StrategySelector<T, S> {

    private val strategyMap = LinkedMultiValueMap<E, S>()

    init {
        enumMappedStrategies.forEach { strategy -> strategyMap.add(strategy.handles(), strategy) }
    }

    override fun selectStrategies(data: T): List<S> = strategyMap[data.selector] ?: emptyList()
}

