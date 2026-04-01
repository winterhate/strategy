package com.winterhate.strategy.service.executors

import com.winterhate.strategy.api.Strategy
import com.winterhate.strategy.api.StrategyExecutor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoggingStrategyExecutor<T>(
    private val strategyExecutorDelegate: StrategyExecutor<T>,
    private val log: Logger = LoggerFactory.getLogger(LoggingStrategyExecutor::class.java)
) : StrategyExecutor<T> {

    override fun executeStrategy(strategy: Strategy<T>, data: T) {
        log.info("Executing strategy {} with {}", strategy.javaClass.simpleName, data)
        strategyExecutorDelegate.executeStrategy(strategy, data)
    }

}
