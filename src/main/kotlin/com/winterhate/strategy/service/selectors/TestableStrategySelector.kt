package com.winterhate.strategy.service.selectors

import com.winterhate.strategy.api.StrategySelector
import com.winterhate.strategy.service.strategies.TestableStrategy

class TestableStrategySelector<T, S : TestableStrategy<T>>(
    testableStrategies: Collection<S>
) : StrategySelector<T, S> {

    private val strategies: List<S> = testableStrategies.sortedBy { it.javaClass.simpleName }

    override fun selectStrategies(data: T): List<S> =
        strategies.filter { strategy -> strategy.isApplicable(data) }
}

