package com.winterhate.strategy.events;

import com.winterhate.strategy.events.dto.EventDto;
import com.winterhate.strategy.service.strategies.ClassMappedStrategy;

public abstract class EventStrategy<T extends EventDto> implements ClassMappedStrategy<T> {
}
