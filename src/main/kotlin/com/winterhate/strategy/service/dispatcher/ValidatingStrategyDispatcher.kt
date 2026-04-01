package com.winterhate.strategy.service.dispatcher

import com.winterhate.strategy.api.StrategyDispatcher
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator

class ValidatingStrategyDispatcher<T>(
    private val strategyDispatcherDelegate: StrategyDispatcher<T>,
    private val validator: Validator
) : StrategyDispatcher<T> {

    override fun dispatch(data: T) {
        val violations = validator.validate(data)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }
        strategyDispatcherDelegate.dispatch(data)
    }
}

