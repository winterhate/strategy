package cz.kb.oleg.strategy.events;

import cz.kb.oleg.strategy.events.dto.EventDto;
import cz.kb.oleg.strategy.service.strategies.AbstractClassMappedStrategy;

public abstract class EventStrategy<T extends EventDto> extends AbstractClassMappedStrategy<T> {
}
