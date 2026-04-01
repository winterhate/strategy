package com.winterhate.strategy.api

fun interface StrategyExecutor<T> {
    fun executeStrategy(strategy: Strategy<T>, data: T)
}

