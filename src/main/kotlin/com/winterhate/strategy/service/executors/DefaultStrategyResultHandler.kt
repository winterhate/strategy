package com.winterhate.strategy.service.executors

import com.winterhate.strategy.api.Strategy
import com.winterhate.strategy.api.StrategyResultHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class DefaultStrategyResultHandler<T>(
    private val log: Logger = LoggerFactory.getLogger(DefaultStrategyResultHandler::class.java),
) : StrategyResultHandler<T> {

    override fun onSuccess(strategy: Strategy<T>, data: T) {
        log.debug("Strategy {} has been successfully executed", strategy)
    }

    override fun onException(strategy: Strategy<T>, data: T, exception: Exception) {
        val strategyName = getStrategyName(strategy)
        log.error("Strategy {} execution failed with error: {}", strategyName, exception.message, exception)
    }

    protected open fun getStrategyName(strategy: Strategy<T>): String = strategy.javaClass.simpleName

}

