package com.winterhate.strategy.events.dto

import jakarta.validation.constraints.NotBlank

open class EventDto(
    @field:NotBlank
    var name: String = "Event Name"
)

