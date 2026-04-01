package com.winterhate.strategy.service.selectors

import com.winterhate.strategy.api.Strategy
import com.winterhate.strategy.api.StrategySelector

class DefaultStrategySelector<T, S : Strategy<T>>(
    strategies: Collection<S>
) : StrategySelector<T, S> {

    private val sortedStrategies: List<S> = strategies.sortedBy { it.javaClass.simpleName }

    override fun selectStrategies(data: T): List<S> = sortedStrategies
}

