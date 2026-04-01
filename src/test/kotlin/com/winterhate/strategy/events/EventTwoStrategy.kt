package com.winterhate.strategy.events

import com.winterhate.strategy.events.dto.EventDto
import com.winterhate.strategy.events.dto.EventTwoDto
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class EventTwoStrategy(
    private val log: Logger
) : EventStrategy<EventDto> {

    override fun getApplicableClass(): Class<out EventDto> = EventTwoDto::class.java

    override fun apply(data: EventDto) {
        val eventTwoDto = data as? EventTwoDto
            ?: throw IllegalArgumentException("Invalid DTO type: ${data::class.java.name}")
        log.info("Handled by EventTwoStrategy: {}", eventTwoDto)
    }

}

