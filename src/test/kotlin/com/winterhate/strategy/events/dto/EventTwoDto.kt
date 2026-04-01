package com.winterhate.strategy.events.dto

import jakarta.validation.constraints.NotBlank

data class EventTwoDto(
    @field:NotBlank
    var detailTwo: String = "EventTwoDetail"
) : EventDto()

