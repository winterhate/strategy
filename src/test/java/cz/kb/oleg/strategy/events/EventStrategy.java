package cz.kb.oleg.strategy.events;

import cz.kb.oleg.strategy.api.TestableStrategy;
import cz.kb.oleg.strategy.events.dto.EventDto;

public abstract class EventStrategy<T extends EventDto> implements TestableStrategy<T> {
}
