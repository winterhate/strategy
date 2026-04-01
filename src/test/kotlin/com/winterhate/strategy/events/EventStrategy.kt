package com.winterhate.strategy.events

import com.winterhate.strategy.events.dto.EventDto
import com.winterhate.strategy.service.strategies.ClassMappedStrategy

interface EventStrategy<T : EventDto> : ClassMappedStrategy<T>
