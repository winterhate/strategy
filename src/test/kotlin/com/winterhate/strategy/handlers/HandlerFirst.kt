package com.winterhate.strategy.handlers

import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class HandlerFirst(private val log: Logger) : HandlerStrategy() {

    override fun handles(): Handlers = Handlers.FIRST

    override fun apply(data: HandlerDto) {
        log.info("Handled by HandlerFirst: {}", data)
    }

}

