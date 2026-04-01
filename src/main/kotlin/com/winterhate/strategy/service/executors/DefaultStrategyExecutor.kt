package com.winterhate.strategy.service.executors

import com.winterhate.strategy.api.Strategy
import com.winterhate.strategy.api.StrategyExecutor
import com.winterhate.strategy.api.StrategyResultHandler

class DefaultStrategyExecutor<T>(
    private val strategyResultHandler: StrategyResultHandler<T> = DefaultStrategyResultHandler()
) : StrategyExecutor<T> {

    override fun executeStrategy(strategy: Strategy<T>, data: T) {
        try {
            strategy.apply(data)
            strategyResultHandler.onSuccess(strategy, data)
        } catch (exception: Exception) {
            strategyResultHandler.onException(strategy, data, exception)
        }
    }
}

