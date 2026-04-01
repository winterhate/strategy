package com.winterhate.strategy.service.dispatcher

import com.winterhate.strategy.api.Strategy
import com.winterhate.strategy.api.StrategyDispatcher
import com.winterhate.strategy.api.StrategyExecutor
import com.winterhate.strategy.api.StrategySelector
import com.winterhate.strategy.service.executors.DefaultStrategyExecutor

class DefaultStrategyDispatcher<T, S : Strategy<T>>(
    private val strategySelector: StrategySelector<T, S>,
    private val strategyExecutor: StrategyExecutor<T> = DefaultStrategyExecutor()
) : StrategyDispatcher<T> {

    override fun dispatch(data: T) {
        strategySelector.selectStrategies(data)
            .forEach { strategy -> strategyExecutor.executeStrategy(strategy, data) }
    }

}

