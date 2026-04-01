package com.winterhate.strategy.api

interface StrategyResultHandler<T> {
    fun onSuccess(strategy: Strategy<T>, data: T)
    fun onException(strategy: Strategy<T>, data: T, exception: Exception)
}

