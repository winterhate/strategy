package com.winterhate.strategy.handlers

import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class HandlerSecond(private val log: Logger) : HandlerStrategy() {

    override fun handles(): Handlers = Handlers.SECOND

    override fun apply(data: HandlerDto) {
        log.info("Handled by HandlerSecond: {}", data)
    }

}

