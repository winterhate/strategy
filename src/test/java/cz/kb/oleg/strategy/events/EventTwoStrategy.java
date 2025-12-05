package cz.kb.oleg.strategy.events;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.events.dto.EventDto;
import cz.kb.oleg.strategy.events.dto.EventTwoDto;
import cz.kb.oleg.strategy.service.result.NoValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventTwoStrategy extends EventStrategy<EventDto> {

    @Override
    public Class<EventTwoDto> getApplicableClass() {
        return EventTwoDto.class;
    }

    @Override
    public Result<NoValue> apply(EventDto dto) {
        if (!(dto instanceof EventTwoDto eventTwoDto)) {
            throw new IllegalArgumentException("Invalid DTO type: " + dto.getClass().getName());
        }
        log.info("Handled by EventTwoStrategy: {}", eventTwoDto);
        return Result.Ok();
    }

}
