package com.winterhate.strategy.service.strategies

import com.winterhate.strategy.api.Strategy

interface EnumMappedStrategy<T, E : Enum<E>> : Strategy<T> {
    fun handles(): E
}

