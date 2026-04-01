package com.winterhate.strategy.events

import com.winterhate.strategy.api.StrategyDispatcher
import com.winterhate.strategy.decisions.dto.DecisionDto
import com.winterhate.strategy.events.dto.EventDto
import com.winterhate.strategy.events.dto.EventOneDto
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class EventOneStrategy(
    private val decisionStrategyDispatcher: StrategyDispatcher<DecisionDto>,
    private val log: Logger
) : EventStrategy<EventDto> {

    override fun getApplicableClass(): Class<out EventDto> = EventOneDto::class.java

    override fun apply(data: EventDto) {
        val eventOneDto = data as? EventOneDto
            ?: throw IllegalArgumentException("Invalid dto type: ${data::class.java}")
        log.info("Handled by EventOneStrategy: {}", eventOneDto)
        decisionStrategyDispatcher.dispatch(DecisionDto("one"))
        decisionStrategyDispatcher.dispatch(DecisionDto("two"))
    }

}

