package cz.kb.oleg.strategy.events;

import cz.kb.oleg.strategy.events.dto.EventDto;
import cz.kb.oleg.strategy.events.dto.EventTwoDto;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventTwoStrategy extends EventStrategy<EventDto> {

    @Override
    @NonNull
    public Class<EventTwoDto> getApplicableClass() {
        return EventTwoDto.class;
    }

    @Override
    public void apply(EventDto dto) {
        if (!(dto instanceof EventTwoDto eventTwoDto)) {
            throw new IllegalArgumentException("Invalid DTO type: " + dto.getClass().getName());
        }
        log.info("Handled by EventTwoStrategy: {}", eventTwoDto);
    }

}
