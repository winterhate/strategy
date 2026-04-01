package com.winterhate.strategy.api

fun interface StrategySelector<T, S : Strategy<T>> {
    fun selectStrategies(data: T): List<S>
}

