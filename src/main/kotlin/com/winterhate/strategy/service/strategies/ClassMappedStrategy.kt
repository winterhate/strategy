package com.winterhate.strategy.service.strategies

import com.winterhate.strategy.api.Strategy

interface ClassMappedStrategy<T> : Strategy<T> {

    fun getApplicableClass(): Class<out T>

}

