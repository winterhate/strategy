package com.winterhate.strategy.decisions.strategies

import com.winterhate.strategy.decisions.DecisionStrategy
import com.winterhate.strategy.decisions.dto.DecisionDto
import org.springframework.stereotype.Component

@Component
class DecisionAllStrategy : DecisionStrategy<DecisionDto> {

    override fun isApplicable(input: DecisionDto): Boolean = true

    override fun apply(data: DecisionDto) {
        throw RuntimeException("Failure in DecisionAllStrategy")
    }
}

