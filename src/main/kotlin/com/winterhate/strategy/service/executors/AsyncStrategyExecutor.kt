package com.winterhate.strategy.service.executors

import com.winterhate.strategy.api.Strategy
import com.winterhate.strategy.api.StrategyExecutor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors.newCachedThreadPool
import java.util.concurrent.TimeUnit.SECONDS

class AsyncStrategyExecutor<T>(
    private val strategyExecutorDelegate: StrategyExecutor<T>,
    private val executor: ExecutorService = newCachedThreadPool(),
    private val log: Logger = LoggerFactory.getLogger(AsyncStrategyExecutor::class.java)
) : StrategyExecutor<T>, DisposableBean {

    override fun executeStrategy(strategy: Strategy<T>, data: T) {
        executor.submit { strategyExecutorDelegate.executeStrategy(strategy, data) }
    }

    override fun destroy() {
        executor.shutdown()
        if (executor.awaitTermination(20, SECONDS)) {
            log.info("AsyncStrategyHandler executor shut down gracefully.")
        } else {
            log.warn("AsyncStrategyHandler executor did not shut down in the allocated time. Forcing shutdown.")
            executor.shutdownNow()
        }
    }

}

