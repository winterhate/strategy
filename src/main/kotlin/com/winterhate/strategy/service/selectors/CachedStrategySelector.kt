package com.winterhate.strategy.service.selectors

import com.winterhate.strategy.api.StrategySelector
import com.winterhate.strategy.service.strategies.ClassMappedStrategy

class CachedStrategySelector<T, S : ClassMappedStrategy<T>>(
    classMappedStrategies: Collection<S>
) : StrategySelector<T, S> {

    private val strategyMap: Map<Class<*>, List<S>> = classMappedStrategies
        .map { it.getApplicableClass() }
        .associateWith { cls ->
            classMappedStrategies.filter { strategy -> cls.isAssignableFrom(strategy.getApplicableClass()) }
        }

    override fun selectStrategies(data: T): List<S> = strategyMap[data!!::class.java] ?: emptyList()
}

