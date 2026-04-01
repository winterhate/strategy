package com.winterhate.strategy.handlers

import com.winterhate.strategy.service.selectors.EnumStrategyData

data class HandlerDto(
    val currentSelector: Handlers,
    val value: String
) : EnumStrategyData<Handlers>(currentSelector)

