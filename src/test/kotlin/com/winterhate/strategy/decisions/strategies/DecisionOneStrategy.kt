package com.winterhate.strategy.decisions.strategies

import com.winterhate.strategy.decisions.DecisionStrategy
import com.winterhate.strategy.decisions.dto.DecisionDto
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class DecisionOneStrategy(
    private val log: Logger
) : DecisionStrategy<DecisionDto> {

    override fun isApplicable(input: DecisionDto): Boolean = input.type == "one"

    override fun apply(data: DecisionDto) {
        log.info("Handled by DecisionOneStrategy: {}", data)
    }

}

