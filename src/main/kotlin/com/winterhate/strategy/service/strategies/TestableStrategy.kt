package com.winterhate.strategy.service.strategies

import com.winterhate.strategy.api.Strategy

interface TestableStrategy<T> : Strategy<T> {
    fun isApplicable(input: T): Boolean
}

