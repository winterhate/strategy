package com.winterhate.strategy.api

fun interface StrategyDispatcher<T> {
    fun dispatch(data: T)
}

